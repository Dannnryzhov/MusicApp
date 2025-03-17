package com.example.musicapp.domain.usecases

import com.example.musicapp.domain.models.TrackEntity
import com.example.musicapp.domain.repository.TracksRepository
import javax.inject.Inject

class SearchDownloadedTracksUseCase @Inject constructor(
    private val repository: TracksRepository
) {
    suspend operator fun invoke(tracks: List<TrackEntity>, query: String): List<TrackEntity> {
        return repository.searchDownloadedTracks(tracks, query)
    }
}