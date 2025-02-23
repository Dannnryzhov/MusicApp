package com.example.musicapp.data.network.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class TracksDto(
    @SerializedName("data") val data: List<TrackDto>
)
