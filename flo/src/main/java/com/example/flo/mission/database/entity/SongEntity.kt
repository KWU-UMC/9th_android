package com.example.flo.mission.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "SongTable")
data class SongEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String = "",
    val artist: String = "",
    val second: Int = 0,
    val playtime: Int = 0,
    val isPlaying: Boolean = false,
    val music: String = "",
    val coverImg: Int? = null,
    val isLike: Boolean = false,
    val albumIdx: Int = 0// Reference Key(=Foreign Key) SongEntity ↔ AlbumEntity
): Parcelable