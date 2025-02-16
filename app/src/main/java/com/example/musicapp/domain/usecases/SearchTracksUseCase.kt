package com.example.musicapp.domain.usecases

import com.example.musicapp.domain.models.TrackEntity
import com.example.musicapp.domain.repository.TracksRepository
import javax.inject.Inject

class SearchTracksUseCase @Inject constructor(
    private val repository: TracksRepository
) {
    suspend operator fun invoke(query: String): List<TrackEntity> {
        return repository.searchTracks(query)
    }
}