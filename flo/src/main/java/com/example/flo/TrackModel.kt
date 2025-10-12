package com.example.flo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrackModel(
    val trackTitle: String,
    val trackArtist: String,
    val trackThumbnail: Int
): Parcelable
