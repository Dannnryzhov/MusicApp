package com.example.musicapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.musicapp.data.database.models.TrackDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DownloadedTrackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: TrackDbEntity)

    @Delete
    suspend fun removeFromDownloaded(track: TrackDbEntity)

    @Query("SELECT * FROM downloaded_tracks")
    fun getDownloadedTracks(): Flow<List<TrackDbEntity>>
}