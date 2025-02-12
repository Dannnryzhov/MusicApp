package com.example.musicapp.data.mapper

import com.example.musicapp.data.network.models.TrackDto
import com.example.musicapp.domain.models.TrackEntity

fun TrackDto.toDomain(): TrackEntity {
    return TrackEntity(
        id = this.id,
        title = this.title,
        artistName = this.artist.name,
        picture = this.album.picture,
        preview = this.preview
    )
}