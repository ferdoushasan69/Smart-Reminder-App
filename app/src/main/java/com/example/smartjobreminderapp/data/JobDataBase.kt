package com.example.smartjobreminderapp.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [JobEntity::class], version = 1)
abstract class JobDataBase : RoomDatabase() {
    abstract fun getDao() : JobDao
}