package com.example.flo.mission.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.flo.mission.database.entity.SongEntity

@Dao
interface SongDao {
    @Insert
    fun insertSong(song: SongEntity)
}