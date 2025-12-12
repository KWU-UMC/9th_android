package com.example.flo.mission.presentation

import android.app.Application
import android.util.Log
import com.example.flo.BuildConfig
import com.example.flo.mission.data.local.room.DatabaseModule
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        DatabaseModule.initialize(context = this)
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
        val keyHash = Utility.getKeyHash(this)
        Log.e("keyHash", keyHash)
    }
}