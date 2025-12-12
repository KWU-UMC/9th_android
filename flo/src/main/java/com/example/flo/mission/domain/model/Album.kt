package com.example.flo.mission.domain.model

data class Album(
    val albumId: Int,
    val albumTitle: String,
    val albumThumbnail: Int,
    val albumArtistName: String,
    val albumReleaseDate: String,
    val albumFormat: String,
    val albumGenre: String,
    val albumState: MusicState = MusicState.RELEASE
)