package com.dumitrachecristian.petapp.data

import android.content.SharedPreferences

class SessionManager(private val sharedPreferences: SharedPreferences) {
    companion object {
        private const val ACCESS_TOKEN = "ACCESS_TOKEN"
    }

    fun setToken(token: String) {
        sharedPreferences.edit().putString(ACCESS_TOKEN, token).apply()
    }

    fun getToken(): String {
        return sharedPreferences.getString(ACCESS_TOKEN, "")!!
    }
}