package com.example.musicapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TrackDbEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun downloadedTrackDao(): DownloadedTrackDao
}