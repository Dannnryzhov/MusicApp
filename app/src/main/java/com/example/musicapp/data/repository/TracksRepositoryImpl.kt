package com.example.musicapp.data.repository

import com.example.musicapp.data.mapper.toDomain
import com.example.musicapp.data.network.DeezerApiService
import com.example.musicapp.domain.models.TrackEntity
import com.example.musicapp.domain.repository.TracksRepository
import javax.inject.Inject

class TracksRepositoryImpl @Inject constructor(
    private val apiService: DeezerApiService
) : TracksRepository {

    override suspend fun getTracksList(index: Int): List<TrackEntity> {
        val response = apiService.getChart(index=index)
        return response.tracks.data.map { it.toDomain() }
    }
}