package com.example.musicapp.domain.repository

import com.example.musicapp.domain.models.TrackEntity

interface TracksRepository {
    suspend fun getTracksList(index: Int): List<TrackEntity>
}