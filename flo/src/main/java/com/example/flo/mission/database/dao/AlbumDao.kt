package com.example.flo.mission.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.flo.mission.database.entity.AlbumEntity

@Dao
interface AlbumDao {
    @Insert
    fun insertAlbum(album: AlbumEntity)
}