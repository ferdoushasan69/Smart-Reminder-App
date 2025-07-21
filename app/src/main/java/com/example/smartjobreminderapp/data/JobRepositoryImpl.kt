package com.example.smartjobreminderapp.data

import com.example.smartjobreminderapp.domain.JobRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.coroutines.Continuation

class JobRepositoryImpl @Inject constructor(
    private val dao: JobDao
): JobRepository {
    override suspend fun getAllJobList(): Flow<List<JobEntity>> {
      return dao.getAllJob()
    }

    override suspend fun insertJob(jobEntity: JobEntity) {
        dao.insertJob(jobEntity)
    }

    override suspend fun deleteJob(jobEntity: JobEntity) {
        dao.deleteJob(jobEntity)
    }

    override suspend fun update(jobEntity: JobEntity) {
        dao.updateJob(jobEntity)
    }
}