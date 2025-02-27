package com.example.musicapp.domain.repository

import com.example.musicapp.domain.models.TrackEntity
import kotlinx.coroutines.flow.Flow

interface TracksRepository {
    suspend fun getTracksList(index: Int): List<TrackEntity>
    suspend fun addToDownloaded(track: TrackEntity)
    suspend fun removeFromDownloaded(track: TrackEntity)
    fun getDownloadedTracks(): Flow<List<TrackEntity>>
    suspend fun searchTracks(query: String): List<TrackEntity>
}
