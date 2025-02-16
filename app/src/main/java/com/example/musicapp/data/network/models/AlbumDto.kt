package com.example.musicapp.data.network.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AlbumDto(
    @SerializedName("id") val id: Int,
    @SerializedName("cover_xl") val picture: String
)
