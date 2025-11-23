package com.example.flo.mission.database

import android.content.Context
import com.example.flo.mission.database.dao.AlbumDao
import com.example.flo.mission.database.dao.SongDao
import com.example.flo.mission.database.database.MusicDatabase

object DatabaseModule {

    private lateinit var musicDatabase: MusicDatabase
    lateinit var songDao: SongDao
    lateinit var albumDao: AlbumDao

    fun initialize(context: Context) {
        musicDatabase = MusicDatabase.getInstance(context = context)
        songDao = musicDatabase.songDao
        albumDao = musicDatabase.albumDao
    }
}