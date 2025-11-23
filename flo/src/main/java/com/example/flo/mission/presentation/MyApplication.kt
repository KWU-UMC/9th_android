package com.example.flo.mission.presentation

import android.app.Application
import com.example.flo.mission.database.DatabaseModule

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        DatabaseModule.initialize(context = this)
    }
}