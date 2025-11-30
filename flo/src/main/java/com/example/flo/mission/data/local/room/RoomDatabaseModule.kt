package com.example.flo.mission.data.local.room

import android.content.Context
import com.example.flo.mission.data.local.room.dao.AlbumDao
import com.example.flo.mission.data.local.room.dao.SongDao
import com.example.flo.mission.data.local.room.dao.UserDao
import com.example.flo.mission.data.local.room.database.MusicDatabase
import com.example.flo.mission.data.local.room.database.UserDatabase

object RoomDatabaseModule {

    private lateinit var musicDatabase: MusicDatabase
    lateinit var songDao: SongDao
    lateinit var albumDao: AlbumDao

    private lateinit var userDatabase: UserDatabase
    lateinit var userDao: UserDao

    fun initialize(context: Context) {
        musicDatabase = MusicDatabase.Companion.getInstance(context = context)
        songDao = musicDatabase.songDao
        albumDao = musicDatabase.albumDao

        userDatabase = UserDatabase.getInstance(context = context)
        userDao = userDatabase.userDao
    }
}