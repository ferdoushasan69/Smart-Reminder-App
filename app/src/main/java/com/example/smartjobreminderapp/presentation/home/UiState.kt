package com.example.smartjobreminderapp.presentation.home

import com.example.smartjobreminderapp.data.JobEntity

interface UiState {
    data object Loading : UiState
    data class Success(val jobs : List<JobEntity>) : UiState
    data class Error(val message : String) : UiState
}