package com.example.flo.mission.presentation.archive

import com.example.flo.mission.database.entity.AlbumEntity

interface OnItemDeleteListener {
    fun onDeleteIconClick(album: AlbumEntity)
}