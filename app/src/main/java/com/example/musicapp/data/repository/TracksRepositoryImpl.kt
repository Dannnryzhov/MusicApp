package com.example.musicapp.data.repository

import com.example.musicapp.data.database.DownloadedTrackDao
import com.example.musicapp.data.mapper.toDbEntity
import com.example.musicapp.data.mapper.toDomain
import com.example.musicapp.data.network.DeezerApiService
import com.example.musicapp.domain.models.TrackEntity
import com.example.musicapp.domain.repository.TracksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TracksRepositoryImpl @Inject constructor(
    private val apiService: DeezerApiService,
    private val downloadedTrackDao: DownloadedTrackDao
) : TracksRepository {

    override suspend fun getTracksList(index: Int): List<TrackEntity> {
        val response = apiService.getChart(index=index)
        return response.tracks.data.map { it.toDomain() }
    }

    override suspend fun addToDownloaded(track: TrackEntity) {
        downloadedTrackDao.insertTrack(track.toDbEntity())
    }

    override suspend fun removeFromDownloaded(track: TrackEntity) {
        downloadedTrackDao.removeFromDownloaded(track.toDbEntity())
    }

    override fun getDownloadedTracks(): Flow<List<TrackEntity>> =
        downloadedTrackDao.getDownloadedTracks().map { list ->
            list.map {
                it.toDomain()
            }
        }

    override suspend fun searchTracks(query: String): List<TrackEntity> {
        val response = apiService.searchTracks(query, limit = 30)
        return response.data.map { it.toDomain() }
    }
}