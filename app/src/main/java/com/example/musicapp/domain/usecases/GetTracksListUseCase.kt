package com.example.musicapp.domain.usecases

import com.example.musicapp.domain.models.TrackEntity
import com.example.musicapp.domain.repository.TracksRepository
import javax.inject.Inject

class GetTracksListUseCase @Inject constructor(
    private val repository: TracksRepository
) {
    suspend operator fun invoke(index: Int): List<TrackEntity> {
        return repository.getTracksList(index)
    }
}