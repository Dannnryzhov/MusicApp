package com.example.musicapp.data.mapper

import com.example.musicapp.data.database.models.AlbumDbEntity
import com.example.musicapp.data.database.models.ArtistDbEntity
import com.example.musicapp.data.database.models.TrackDbEntity
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

fun TrackEntity.toDbEntity(): TrackDbEntity {
    return TrackDbEntity(
        id = this.id,
        title = this.title,
        preview = this.preview,
        artist = ArtistDbEntity(
            id = this.artist.id,
            name = this.artist.name
        ),
        album = AlbumDbEntity(
            id = this.album.id,
            cover = this.album.cover
        )
    )
}

fun TrackDbEntity.toDomain(): TrackEntity {
    return TrackEntity(
        id = this.id,
        title = this.title,
        preview = this.preview,
        artist = ArtistEntity(
            id = this.artist.id,
            name = this.artist.name
        ),
        album = AlbumEntity(
            id = this.album.id,
            cover = this.album.cover
        )
    )
}