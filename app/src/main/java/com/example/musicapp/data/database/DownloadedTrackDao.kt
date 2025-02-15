package com.example.musicapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DownloadedTrackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: TrackDbEntity)

    @Delete
    suspend fun deleteTrack(track: TrackDbEntity)

    @Query("SELECT * FROM downloaded_tracks")
    fun getFavoriteTracks(): Flow<List<TrackDbEntity>>
}