package com.example.flo.mission.presentation

import android.app.Application
import com.example.flo.mission.data.local.room.RoomDatabaseModule

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        RoomDatabaseModule.initialize(context = this)
    }
}