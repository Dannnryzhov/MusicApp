package com.example.musicapp.data.repository

import com.example.musicapp.data.mapper.toDomain
import com.example.musicapp.data.network.RetrofitClient
import com.example.musicapp.domain.models.TrackEntity
import com.example.musicapp.domain.repository.TracksRepository

class TracksRepositoryImpl : TracksRepository {

    private val apiService = RetrofitClient.deezerApiService

    override suspend fun getTracksList(): List<TrackEntity> {
        val response = apiService.getChart()
        return response.tracks.data.map { it.toDomain() }
    }
}