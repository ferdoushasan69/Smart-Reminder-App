package com.example.smartjobreminderapp.domain

import com.example.smartjobreminderapp.data.JobEntity
import kotlinx.coroutines.flow.Flow

interface JobRepository {
    suspend fun getAllJobList() : Flow<List<JobEntity>>
    suspend fun insertJob(jobEntity: JobEntity)
    suspend fun deleteJob(jobEntity: JobEntity)
    suspend fun update(jobEntity: JobEntity)
}