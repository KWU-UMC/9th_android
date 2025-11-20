package com.example.flo.mission.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.flo.mission.database.entity.SongEntity

@Dao
interface SongDao {
    @Insert
    fun insertSong(song: SongEntity)

    @Update
    fun updateSong(song: SongEntity)

    @Query("SELECT * FROM SongTable")
    fun getAllSongs(): List<SongEntity>

    @Query("SELECT * FROM SongTable WHERE id = :id")
    fun getSongById(id: Int): SongEntity

    @Query("SELECT * FROM SongTable WHERE artist = :artist")
    fun getSongsByArtist(artist: String): List<SongEntity>
}