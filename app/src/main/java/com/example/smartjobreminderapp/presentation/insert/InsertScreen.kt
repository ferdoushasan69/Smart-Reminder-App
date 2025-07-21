package com.example.smartjobreminderapp.presentation.insert

import android.text.format.DateUtils
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.smartjobreminderapp.R
import com.example.smartjobreminderapp.data.JobEntity
import com.example.smartjobreminderapp.presentation.CustomTopBar
import com.example.smartjobreminderapp.presentation.theme.PrimaryColor
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertScreen(navController: NavHostController, viewModel: InsertViewModel = hiltViewModel()) {

    val state = viewModel.uiState.collectAsState()
    var title by remember { mutableStateOf("") }
    var company by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }
    var deadlineTimestamp by rememberSaveable { mutableStateOf<Long?>(null) }

    val context = LocalContext.current
    val dateState = rememberDatePickerState()
    var dateToString = dateState.selectedDateMillis?.let {
        SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(it))
    } ?: "Choose Date"

    var showDialog by remember { mutableStateOf(false) }
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        CustomTopBar(title = "Create Job", isInsertScreen = true, onBack = {
            navController.navigateUp()
        })
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CustomTextField(
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { title = it },
                textValue = title,
                placeHolderText = stringResource(R.string.enter_your_title)
            )
            Spacer(Modifier.height(8.dp))
            CustomTextField(
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { company = it },
                textValue = company,
                placeHolderText = stringResource(R.string.enter_your_company_name)
            )
            Spacer(Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        showDialog = true

                    }
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant.copy(.7f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(16.dp), contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = dateToString,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            if (showDialog) {
                DatePickerDialog(
                    onDismissRequest = { showDialog = false },
                    confirmButton = {

                        Button(
                            onClick = {
                                deadlineTimestamp = dateState.selectedDateMillis

                                showDialog = false
                            }
                        ) {
                            Text(text = "OK")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = { showDialog = false }
                        ) {
                            Text(text = "Cancel")
                        }
                    }
                ) {
                    DatePicker(
                        state = dateState,
                        showModeToggle = true
                    )
                }
            }

            Spacer(Modifier.height(8.dp))
            CustomTextField(
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { status = it },
                textValue = status,
                placeHolderText = "Enter your company status"
            )
            Spacer(Modifier.height(8.dp))

            Button(
                onClick = {
                    val jobEntity = JobEntity(
                        title = "Job title : $title",
                        companyName = "Company name : $company",
                        deadLine = deadlineTimestamp?:0L,
                        status = "Status : $status"
                    )
                    viewModel.insertJob(jobEntity)
                    viewModel.scheduleDeadlineReminder(context = context, job = jobEntity)
                    navController.navigateUp()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor)
            ) {
                Text("Save", color = Color.White)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    label: String = "Select Deadline",
    initialDate: Long? = null,
    onDateSelected: (selectedDate: Long?) -> Unit
) {
    var selectedDate by rememberSaveable { mutableStateOf(initialDate) }
    var showDatePicker by remember { mutableStateOf(false) }

    val formattedDate = selectedDate?.let {
        SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(it))
    } ?: ""

    OutlinedTextField(
        value = formattedDate,
        onValueChange = {},
        label = { Text(label) },
        placeholder = { Text("Pick a date") },
        trailingIcon = {
            Icon(Icons.Default.DateRange, contentDescription = "Select date")
        },
        readOnly = true,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showDatePicker = true } // ✅ Apply here
    )

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(initialSelectedDateMillis = selectedDate)
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    selectedDate = datePickerState.selectedDateMillis
                    onDateSelected(selectedDate)
                    showDatePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}
