package com.example.flo.mission.data.local.room

import android.content.Context
import com.example.flo.mission.data.local.room.dao.AlbumDao
import com.example.flo.mission.data.local.room.dao.SongDao
import com.example.flo.mission.data.local.room.database.MusicDatabase

object RoomDatabaseModule {

    private lateinit var musicDatabase: MusicDatabase
    lateinit var songDao: SongDao
    lateinit var albumDao: AlbumDao

    fun initialize(context: Context) {
        musicDatabase = MusicDatabase.Companion.getInstance(context = context)
        songDao = musicDatabase.songDao
        albumDao = musicDatabase.albumDao
    }
}