package com.example.flo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlbumModel(
    val title: String,
    val singer: String,
    val thumbnail: Int
): Parcelable
