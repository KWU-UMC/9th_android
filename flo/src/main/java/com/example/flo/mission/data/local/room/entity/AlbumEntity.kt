package com.example.flo.mission.data.local.room.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.flo.mission.domain.model.MusicState
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "AlbumTable")
data class AlbumEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String = "",
    val artist: String = "",
    val image: Int = 0,
    val releaseDate: String = "",
    val format: String = "",
    val genre: String = "",
    val playState: MusicState = MusicState.RELEASE,
    val songs: List<SongEntity>? = null,
    val isLike: Boolean = false,
): Parcelable