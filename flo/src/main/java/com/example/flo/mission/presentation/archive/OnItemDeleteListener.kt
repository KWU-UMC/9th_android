package com.example.flo.mission.presentation.archive

import com.example.flo.mission.data.local.room.entity.AlbumEntity

interface OnItemDeleteListener {
    fun onDeleteIconClick(album: AlbumEntity)
}