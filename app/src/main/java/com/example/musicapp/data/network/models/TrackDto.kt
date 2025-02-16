package com.example.musicapp.data.network.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class TrackDto(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("preview") val preview:String,
    @SerializedName("artist") val artist: ArtistDto,
    @SerializedName("album") val album: AlbumDto
)
