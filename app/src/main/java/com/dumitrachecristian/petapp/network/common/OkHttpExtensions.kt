package com.dumitrachecristian.petapp.network.common

import android.util.Log
import okhttp3.Request
import okhttp3.Response

val Response.retryCount: Int
    get() {
        var currentResponse = priorResponse
        var result = 0
        while (currentResponse != null) {
            result++
            currentResponse = currentResponse.priorResponse
        }
        return result
    }

fun Response.createSignedRequest(tokenEvenIfExpired: String): Request? = try {
    request.signWithToken(tokenEvenIfExpired)
} catch (error: Throwable) {
    Log.d("OkHttpResponse","Failed to re-sign request")
    null
}

fun Request.signWithToken(token: String): Request {
    return this.newBuilder()
        .header("Authorization", token)
        .build()
}