package com.example.flo.mission.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "AlbumTable")
data class AlbumEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String = "",
    val artist: String = "",
    val image: Int? = null,
    val songs: List<SongEntity>? = null,
    val isLike: Boolean = false,
): Parcelable