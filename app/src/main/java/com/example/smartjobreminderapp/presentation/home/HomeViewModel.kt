package com.example.smartjobreminderapp.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartjobreminderapp.data.JobEntity
import com.example.smartjobreminderapp.domain.JobRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: JobRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState : StateFlow<UiState> = _uiState.asStateFlow()

    fun getAllJob() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
          try {

              repository.getAllJobList().collect {
              _uiState.value = UiState.Success(it)
              }
          }catch (e : Exception){
              e.printStackTrace()
              _uiState.value = UiState.Error(e.cause.toString())
              Log.e("HomeViewModel", "getAllJob: ${e.cause}", )
          }
        }
    }
}