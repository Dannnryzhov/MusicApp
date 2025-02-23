package com.example.musicapp.domain.models

data class TrackEntity(
    val id: Long,
    val title: String,
    val artist: ArtistEntity,
    val album: AlbumEntity,
    val preview: String
)
