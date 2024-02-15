package com.dumitrachecristian.petapp.data.datasource

import retrofit2.Response
import com.dumitrachecristian.petapp.network.Result

interface BaseRemoteDataSource {

    suspend fun <T : Any> safeApiResult(call: suspend () -> Response<T>): Result<T> {
        return try {
            val response = call.invoke()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Result.Success(body)
            } else {
                Result.Error(response.code(), response.message())
            }
        } catch (exception: Exception) {
            Result.Error(-1, exception.message)
        }
    }
}