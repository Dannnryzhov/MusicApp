package com.example.musicapp.data.network

import com.example.musicapp.data.network.models.TracksResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DeezerApiService {

    @GET("chart")
    suspend fun getChart(
        @Query("limit") limit: Int = 30,
        @Query("index") index: Int = 0
    ): TracksResponse
}
