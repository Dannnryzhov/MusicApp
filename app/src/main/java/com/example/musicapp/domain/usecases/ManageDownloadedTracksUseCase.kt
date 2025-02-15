package com.example.musicapp.domain.usecases

import com.example.musicapp.domain.models.TrackEntity
import com.example.musicapp.domain.repository.TracksRepository
import javax.inject.Inject

class ManageDownloadedTracksUseCase @Inject constructor (
    private val repository: TracksRepository
) {
    suspend fun add(track: TrackEntity) = repository.addToDownloaded(track)
    suspend fun remove(track: TrackEntity) = repository.removeFromDownloaded(track)
    fun getFavorites() = repository.getDownloadedTracks()
}