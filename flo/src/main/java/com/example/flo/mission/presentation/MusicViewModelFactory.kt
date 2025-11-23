package com.example.flo.mission.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.flo.mission.database.dao.AlbumDao
import com.example.flo.mission.database.dao.SongDao

class MusicViewModelFactory(
    private val albumDao: AlbumDao,
    private val songDao: SongDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return MusicViewModel(albumDao = albumDao, songDao = songDao) as T
    }
}