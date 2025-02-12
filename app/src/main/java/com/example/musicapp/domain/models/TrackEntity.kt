package com.example.musicapp.domain.models

data class TrackEntity(
    val id: Int,
    val title: String,
    val artistName: String,
    val picture: String,
    val preview: String
)
