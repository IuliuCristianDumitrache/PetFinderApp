package com.dumitrachecristian.petapp.network

import com.dumitrachecristian.petapp.data.TokenManager
import com.dumitrachecristian.petapp.network.common.AuthorizationType
import com.dumitrachecristian.petapp.network.common.signWithToken
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().signedRequest()
        return chain.proceed(newRequest)
    }

    private fun Request.signedRequest() = when (AuthorizationType.fromRequest(this)) {
        AuthorizationType.ACCESS_TOKEN -> this.signWithToken(tokenManager.getAccessToken())
        AuthorizationType.NONE -> this
    }
}