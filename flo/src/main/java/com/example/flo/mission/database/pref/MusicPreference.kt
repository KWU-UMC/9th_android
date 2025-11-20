package com.example.flo.mission.database.pref

import android.content.Context
import com.example.flo.mission.database.dao.SongDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

class MusicPreference(context: Context) {

    private val preference = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveSongId(songDao: SongDao) {
        CoroutineScope(Dispatchers.IO).launch {
            val songs = songDao.getAllSongs()
            for(song in songs) {
                preference.edit().putInt(UUID.randomUUID().toString(), song.id).apply()
            }
        }
    }

    fun getSongId(): List<Int> {
        return preference.all.values.filterIsInstance<Int>()
    }

    companion object {
        private const val PREFS_NAME = "prefs_music"
    }
}