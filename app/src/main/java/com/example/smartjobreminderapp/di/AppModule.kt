package com.example.smartjobreminderapp.di

import android.content.Context
import androidx.room.Room
import com.example.smartjobreminderapp.data.JobDao
import com.example.smartjobreminderapp.data.JobDataBase
import com.example.smartjobreminderapp.data.JobRepositoryImpl
import com.example.smartjobreminderapp.domain.JobRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Singleton
    @Provides
    fun provideRoomDb(@ApplicationContext context: Context): JobDataBase {
        return Room.databaseBuilder(context = context, JobDataBase::class.java, "job_db").build()
    }

    @Singleton
    @Provides
    fun provideJobDao(db: JobDataBase): JobDao {
        return db.getDao()
    }

    @Singleton
    @Provides
    fun provideJobRepository(jobDao: JobDao): JobRepository{
        return JobRepositoryImpl(dao = jobDao)
    }
}