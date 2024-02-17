package com.dumitrachecristian.petapp.data

import android.content.SharedPreferences
import com.dumitrachecristian.petapp.model.AccessTokenResponse
import com.dumitrachecristian.petapp.network.ApiService
import dagger.Lazy
import javax.inject.Inject

class TokenManager @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val apiService: Lazy<ApiService>
) {

    companion object {
        private const val ACCESS_TOKEN = "ACCESS_TOKEN"
        private const val ACCESS_TOKEN_EXPIRES_IN_KEY = "TOKEN_EXPIRES_IN_KEY"
    }

    fun getAccessToken(): String {
        return sharedPreferences.getString(ACCESS_TOKEN, "") ?: ""
    }

    fun getAccessTokenValid(): String {
        return if(isAccessTokenExpired()) {
            return fetchNewTokenCall() ?: getAccessToken()
        } else {
            getAccessToken()
        }
    }

    /**
     * Function signatures of both Interceptor and Authenticator require the request to be created or transformed synchronously.
     * The process of refreshing an access token is likely asynchronous due to requiring its own web request to an OAuth API.
     * We therefore need to call this asynchronous token refresh process synchronously
     */
    private fun fetchNewTokenCall(): String? {
        val refreshTokenResponse = apiService.get().requestToken().execute()
        return if (refreshTokenResponse.isSuccessful) {
            saveToken(refreshTokenResponse.body()!!)
            getAccessToken()
        } else {
            null
        }
    }

    private fun isAccessTokenExpired(): Boolean {
        val tokenExpirationTime = sharedPreferences.getLong(ACCESS_TOKEN_EXPIRES_IN_KEY, 0.toLong())
        return if(tokenExpirationTime == 0.toLong()) {
            true
        } else {
            System.currentTimeMillis() >= tokenExpirationTime
        }
    }

    private fun setAccessTokenExpiresIn(expiresIn: Int) {
        sharedPreferences.edit().putLong(ACCESS_TOKEN_EXPIRES_IN_KEY, (System.currentTimeMillis() + expiresIn)).apply()
    }

    private fun setAccessToken(accessToken: String) {
        sharedPreferences.edit().putString(ACCESS_TOKEN, accessToken).apply()
    }

    private fun saveToken(tokenResponse: AccessTokenResponse) {
        val token = tokenResponse.tokenType + " " + tokenResponse.accessToken
        if (token.trim().isNotEmpty()) {
            setAccessToken(token)
        }
        setAccessTokenExpiresIn(tokenResponse.expiresIn)
    }

    fun logout() {
        setAccessToken("")
        setAccessTokenExpiresIn(0)
    }
}