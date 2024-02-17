package com.dumitrachecristian.petapp.network

import com.dumitrachecristian.petapp.data.TokenManager
import com.dumitrachecristian.petapp.network.common.createSignedRequest
import com.dumitrachecristian.petapp.network.common.retryCount
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class RefreshTokenAuthenticator @Inject constructor(
    private val tokenManager: TokenManager
): Authenticator {

    companion object {
        private const val API_MAX_RETRIES = 2
    }

    override fun authenticate(route: Route?, response: Response): Request? = when {
        response.retryCount > API_MAX_RETRIES -> {
            null
        }
        else -> response.createSignedRequest(tokenManager.getAccessTokenValid())
    }
}