package com.example.musicapp.data.database.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "downloaded_tracks")
data class TrackDbEntity (
    @PrimaryKey val id: Long,
    val title: String,
    val preview: String,
    @Embedded(prefix = "artist_") val artist: ArtistDbEntity,
    @Embedded(prefix = "album_") val album: AlbumDbEntity

)