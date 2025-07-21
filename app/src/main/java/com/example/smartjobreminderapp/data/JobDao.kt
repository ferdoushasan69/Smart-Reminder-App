package com.example.smartjobreminderapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface JobDao {

    @Insert
    suspend fun insertJob(jobEntity: JobEntity)

    @Update
    suspend fun updateJob(jobEntity: JobEntity)

    @Delete
    suspend fun deleteJob(jobEntity: JobEntity)

    @Query("SELECT * FROM job_entity")
    fun getAllJob() : Flow<List<JobEntity>>
}