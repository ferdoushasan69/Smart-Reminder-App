package com.example.smartjobreminderapp.presentation.home

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.smartjobreminderapp.presentation.NotificationHelper

class DeadlineReminderWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val title = inputData.getString("title") ?: "Job Reminder"
        val company = inputData.getString("company") ?: "Upcoming Deadline"

        NotificationHelper.showNotification(
            applicationContext,
            title,
            "You have 24 hours left for $company job deadline."
        )

        return Result.success()
    }
}
