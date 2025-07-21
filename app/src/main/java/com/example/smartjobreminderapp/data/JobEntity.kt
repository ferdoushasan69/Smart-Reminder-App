package com.example.smartjobreminderapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "job_entity")
data class JobEntity(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val title : String,
    val companyName : String,
    val deadLine : Long,
    val status : String
)
