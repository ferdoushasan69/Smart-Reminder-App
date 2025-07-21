package com.example.smartjobreminderapp.presentation.insert

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.smartjobreminderapp.data.JobEntity
import com.example.smartjobreminderapp.domain.JobRepository
import com.example.smartjobreminderapp.presentation.home.DeadlineReminderWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class InsertViewModel @Inject constructor(
    private val repository: JobRepository
) : ViewModel() {
    var uiState = MutableStateFlow("")
        private set
    fun insertJob(jobEntity: JobEntity){
      viewModelScope.launch {
          try {
              repository.insertJob(jobEntity)
          }catch (e : Exception){
              uiState.value = e.cause.toString()
          }
      }
    }

    fun scheduleDeadlineReminder(job: JobEntity,context : Context) {
        val delayMillis = job.deadLine - System.currentTimeMillis() - (24 * 60 * 60 * 1000)

        if (delayMillis > 0) {
            val workData = Data.Builder()
                .putString("title", job.title)
                .putString("company", job.companyName)
                .build()

            val workRequest = OneTimeWorkRequestBuilder<DeadlineReminderWorker>()
                .setInitialDelay(delayMillis, TimeUnit.MILLISECONDS)
                .setInputData(workData)
                .build()

            WorkManager.getInstance(context).enqueue(workRequest)
        }
    }

}