package com.example.digivice_android_app.framework.utils

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreferencesManager @Inject constructor(private val sharedPreferences: SharedPreferences) {

    companion object {
        private const val LOGIN_PREFERENCES = "LOGIN_PREFERENCES"
        private const val USERNAME_PREFERENCES = "USERNAME_PREFERENCES"
    }

    fun isUserLogged(): Boolean =
        sharedPreferences.getBoolean(LOGIN_PREFERENCES, false)

    fun setUserLogged(isLogged: Boolean) {
        sharedPreferences
            .edit()
            .putBoolean(LOGIN_PREFERENCES, isLogged)
            .apply()
    }

    fun getUsernamePreference(): String? =
        sharedPreferences.getString(USERNAME_PREFERENCES, null)

    fun setUsernamePreference(username: String) {
        sharedPreferences
            .edit()
            .putString(USERNAME_PREFERENCES, username)
            .apply()
    }
}