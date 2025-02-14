package com.example.musicapp.data.mapper

import com.example.musicapp.data.network.models.AlbumDto
import com.example.musicapp.data.network.models.ArtistDto
import com.example.musicapp.data.network.models.TrackDto
import com.example.musicapp.data.network.models.TracksDto
import com.example.musicapp.data.network.models.TracksResponse
import com.example.musicapp.domain.models.AlbumEntity
import com.example.musicapp.domain.models.ArtistEntity
import com.example.musicapp.domain.models.TrackEntity

fun ArtistDto.toDomain(): ArtistEntity {
    return ArtistEntity(
        id = this.id,
        name = this.name
    )
}
fun AlbumDto.toDomain(): AlbumEntity {
    return AlbumEntity(
        id = this.id,
        cover = this.picture
    )
}
fun TrackDto.toDomain(): TrackEntity {
    return TrackEntity(
        id = this.id,
        title = this.title,
        preview = this.preview,
        artist = this.artist.toDomain(),
        album = this.album.toDomain()
    )
}
fun TracksDto.toDomainList(): List<TrackEntity> {
    return this.data.map { it.toDomain() }
}