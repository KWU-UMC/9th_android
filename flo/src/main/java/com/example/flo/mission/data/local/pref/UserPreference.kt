package com.example.flo.mission.data.local.pref

import android.content.Context

class UserPreference(context: Context) {

    private val preference = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setUserId(id: Int) {
        preference.edit().putInt(KEY_USER_ID, id).apply()
    }

    fun getUserId(): Int {
        return preference.getInt(KEY_USER_ID, -1)
    }

    companion object {
        private const val PREFS_NAME = "prefs_user"
        private const val KEY_USER_ID = "key_user_id"
    }
}