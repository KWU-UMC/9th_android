package com.example.flo.mission.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Track(
    val trackId: Int? = null,
    val trackTitle: String,
    val trackArtist: String,
    val trackThumbnail: Int? = null
): Parcelable