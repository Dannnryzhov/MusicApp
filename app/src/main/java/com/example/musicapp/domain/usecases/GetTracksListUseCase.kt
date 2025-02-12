package com.example.musicapp.domain.usecases

import com.example.musicapp.domain.models.TrackEntity
import com.example.musicapp.domain.repository.TracksRepository

class GetTracksListUseCase (
    private val repository: TracksRepository
) {
    suspend operator fun invoke(): List<TrackEntity> {
        return repository.getTracksList()
    }
}