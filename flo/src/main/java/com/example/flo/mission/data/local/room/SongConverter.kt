package com.example.flo.mission.data.local.room

import androidx.room.TypeConverter
import com.example.flo.mission.data.local.room.entity.SongEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SongConverter {
    @TypeConverter
    fun trackListToString(tracks: List<SongEntity>): String {
        return Gson().toJson(tracks)
    }

    @TypeConverter
    fun stringToTrackList(json: String): List<SongEntity> {
        val type = object : TypeToken<List<SongEntity>>(){}.type
        return Gson().fromJson(json, type)
    }
}