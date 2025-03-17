package com.example.musicapp.di

import android.app.Application
import androidx.room.Room
import com.example.musicapp.data.database.AppDatabase
import com.example.musicapp.data.database.DownloadedTrackDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase =
        Room.databaseBuilder(application, AppDatabase::class.java, "music_app.db")
            .build()

    @Provides
    fun provideDownloadedTrackDao(database: AppDatabase): DownloadedTrackDao =
        database.downloadedTrackDao()
}