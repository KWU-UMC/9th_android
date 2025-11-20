package com.example.flo.mission.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flo.mission.database.entity.SongEntity

class MusicViewModel: ViewModel() {

    private val _songResources: MutableLiveData<List<SongEntity>> = MutableLiveData()
    val songResources: LiveData<List<SongEntity>> get() = _songResources

    fun setSongResources(songResources: List<SongEntity>) {
        _songResources.value = songResources
    }
}