package com.example.flo.mission.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.flo.mission.database.entity.AlbumEntity

@Dao
interface AlbumDao {
    @Insert
    fun insertAlbum(album: AlbumEntity): Long

    @Query("SELECT * FROM AlbumTable")
    fun getAllAlbums(): List<AlbumEntity>

    @Update
    fun updateAlbum(album: AlbumEntity)

}