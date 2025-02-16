package com.example.musicapp.data.network.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class TracksResponse(
    @SerializedName("tracks") val tracks: TracksDto
)
