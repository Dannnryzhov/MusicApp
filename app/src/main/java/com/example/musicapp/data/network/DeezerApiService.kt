package com.example.musicapp.data.network

import com.example.musicapp.data.network.models.TracksDto
import com.example.musicapp.data.network.models.TracksResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DeezerApiService {

    @GET("chart")
    suspend fun getChart(
        @Query("limit") limit: Int = 30,
        @Query("index") index: Int
    ): TracksResponse

    @GET("search")
    suspend fun searchTracks(
        @Query("q") query: String,
        @Query("limit") limit: Int = 30
    ): TracksDto
}
