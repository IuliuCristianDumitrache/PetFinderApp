package com.dumitrachecristian.petapp.network

import com.dumitrachecristian.petapp.BuildConfig
import com.dumitrachecristian.petapp.model.AccessTokenResponse
import com.dumitrachecristian.petapp.model.Animal
import com.dumitrachecristian.petapp.model.PetModelResponse
import com.dumitrachecristian.petapp.model.TypeResponse
import com.dumitrachecristian.petapp.network.common.AuthorizationType
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Tag

interface ApiService {
    @FormUrlEncoded
    @POST("oauth2/token")
    fun requestToken(
        @Field("grant_type") type: String = "client_credentials",
        @Field("client_id") clientId: String = BuildConfig.PET_API_KEY,
        @Field("client_secret") clientSecret: String = BuildConfig.PET_API_SECRET,
        @Tag authorization: AuthorizationType = AuthorizationType.NONE,
    ): Call<AccessTokenResponse>

    @GET("animals")
    suspend fun getPets(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("type") type: String? = null,
        @Query("gender") gender: String? = null
    ): Response<PetModelResponse>

    @GET("animals/{id}")
    suspend fun getPetDetails(
        @Path(value = "id", encoded = true) id: String
    ): Response<Animal>

    @GET("types")
    suspend fun getFilters(): Response<TypeResponse>
}