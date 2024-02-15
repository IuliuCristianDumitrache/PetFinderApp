package com.dumitrachecristian.petapp.data.datasource

import com.dumitrachecristian.petapp.BuildConfig
import com.dumitrachecristian.petapp.data.SessionManager
import com.dumitrachecristian.petapp.model.AccessTokenResponse
import com.dumitrachecristian.petapp.model.Animal
import com.dumitrachecristian.petapp.model.PetModelResponse
import com.dumitrachecristian.petapp.network.ApiService
import com.dumitrachecristian.petapp.network.Result
import javax.inject.Inject

class PetRemoteDataSource @Inject constructor(
    private val apiService: ApiService,
    val sessionManager: SessionManager
): BaseRemoteDataSource {

    suspend fun requestToken(): Result<AccessTokenResponse> {
        return safeApiResult {
            apiService.requestToken("client_credentials", BuildConfig.PET_API_KEY, BuildConfig.PET_API_SECRET)
        }
    }

    suspend fun getPets(token: String, page: Int, limit: Int): Result<PetModelResponse> {
        return safeApiResult {
            apiService.getPets(token, page, limit)
        }
    }

    suspend fun getPetDetails(): Result<Animal> {
        return safeApiResult {
            apiService.getPetDetails("", "")
        }
    }
}