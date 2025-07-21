package com.example.smartjobreminderapp.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.smartjobreminderapp.presentation.CustomTopBar
import com.example.smartjobreminderapp.presentation.Screen
import com.example.smartjobreminderapp.presentation.theme.PrimaryColor
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAllJob()
    }
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        CustomTopBar(title = "Smart Job Reminder")
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = {},
            modifier = Modifier,
            shape = CircleShape,
            containerColor = PrimaryColor,
            contentColor = Color.White,
            elevation = FloatingActionButtonDefaults.elevation(2.dp),
            content = {
                IconButton(onClick = {
                    navController.navigate(Screen.Insert)
                }) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null
                    )
                }
            }
        )
    }) {

        when(uiState){
            is UiState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    CircularProgressIndicator()
                }
            }
            is UiState.Success -> {
                val jobs = (uiState as UiState.Success).jobs
                if (jobs.isEmpty()){
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        Text("List is empty")
                    }
                }else{
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                            .padding(16.dp)
                    ) {
                        items(jobs, key = {item-> item.id
                        }) { jobInfo ->
                            JobItem(
                                modifier = Modifier.padding(vertical = 8.dp),
                                title = jobInfo.title,
                                companyName = jobInfo.companyName,
                                deadLine = jobInfo.deadLine,
                                status = jobInfo.status
                            )
                        }

                    }
                }

            }
        }
    }

}
fun formatDate(millis: Long): String {
    val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

@Composable
fun JobItem(
    modifier: Modifier = Modifier,
    title: String,
    companyName: String,
    deadLine: Long,
    status: String
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                title.trim(),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(Modifier.height(8.dp))
            Text(companyName.trim(), style = MaterialTheme.typography.bodySmall)
            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Dead line : ${formatDate(deadLine)}".trim(), style = MaterialTheme.typography.bodySmall)
                Text(status.trim(), style = MaterialTheme.typography.bodySmall)

            }
        }
    }

}