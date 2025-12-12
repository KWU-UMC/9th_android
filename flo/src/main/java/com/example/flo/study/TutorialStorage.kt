package com.example.flo.study

import android.content.Context
import android.content.SharedPreferences

class TutorialStorage(context: Context) {
    private val preference: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun isTutorialCompleted(): Boolean {
        return preference.getBoolean(KEY_TUTORIAL_COMPLETION, false)
    }

    fun setTutorialCompleted() {
        val editor = preference.edit()
        editor.putBoolean(KEY_TUTORIAL_COMPLETION, true).apply()
    }

    companion object {
        private const val PREFS_NAME = "prefs_tutorial"
        private const val KEY_TUTORIAL_COMPLETION = "key_tutorial_completion"
    }
}