package com.example.flo.mission.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AlbumTable")
data class AlbumEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String = "",
    val artist: String = "",
    val thumbnail: Int? = null,
    val songs: List<SongEntity>? = null,
    val isLike: Boolean = false,
)