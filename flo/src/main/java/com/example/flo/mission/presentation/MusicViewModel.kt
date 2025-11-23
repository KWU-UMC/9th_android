package com.example.flo.mission.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flo.mission.database.entity.AlbumEntity
import com.example.flo.mission.database.entity.SongEntity

class MusicViewModel: ViewModel() {

    private val _songResources: MutableLiveData<List<SongEntity>> = MutableLiveData()
    val songResources: LiveData<List<SongEntity>> get() = _songResources

    fun setSongResources(songResources: List<SongEntity>) {
        _songResources.value = songResources
    }

    private val _albumResources: MutableLiveData<List<AlbumEntity>> = MutableLiveData()
    val albumResources: LiveData<List<AlbumEntity>> get() = _albumResources

    fun setAlbumResources(albumResources: List<AlbumEntity>) {
        _albumResources.value = albumResources
    }

    fun updateAlbumResources(oldAlbum: AlbumEntity, newAlbum: AlbumEntity) {
        val albumResources = _albumResources.value.toMutableList()
        val updateIndex = albumResources.indexOf(oldAlbum)
        albumResources[updateIndex] = newAlbum
        _albumResources.value = albumResources
    }
}