package com.example.musicapp.data.network.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ArtistDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)
