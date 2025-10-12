package com.example.flo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrackModel(
    val trackId: Int? = null,
    val trackTitle: String,
    val trackArtist: String,
    val trackThumbnail: Int? = null
): Parcelable
