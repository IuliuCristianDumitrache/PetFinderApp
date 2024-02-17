package com.dumitrachecristian.petapp.network

import com.dumitrachecristian.petapp.data.TokenManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiServiceProvider {

    private const val BASE_URL = "https://api.petfinder.com/v2/"

    fun getClient(tokenManager: TokenManager): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient(tokenManager))
            .build()
            .create(ApiService::class.java)
    }

    private fun getOkHttpClient(tokenManager: TokenManager): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(AuthorizationInterceptor(tokenManager))
            .authenticator(RefreshTokenAuthenticator(tokenManager))
            .addInterceptor(loggingInterceptor)
            .build()
    }
}