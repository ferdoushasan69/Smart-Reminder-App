package com.example.smartjobreminderapp.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(title : String,isInsertScreen : Boolean = false,onBack : ()-> Unit={}) {
    TopAppBar(
        title = {
            Text(title, color = MaterialTheme.colorScheme.onBackground)
        },
        navigationIcon = {
            if (isInsertScreen){
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack,contentDescription = null)
                }
            }
        },
    )
}