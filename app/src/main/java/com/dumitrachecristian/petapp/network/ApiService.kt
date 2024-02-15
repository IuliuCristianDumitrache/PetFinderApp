package com.dumitrachecristian.petapp.network

import com.dumitrachecristian.petapp.model.AccessTokenResponse
import com.dumitrachecristian.petapp.model.Animal
import com.dumitrachecristian.petapp.model.PetModelResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("oauth2/token")
    suspend fun requestToken(
        @Field("grant_type") type: String,
        @Field(value = "client_id") clientId: String,
        @Field(value = "client_secret") clientSecret: String
    ): Response<AccessTokenResponse>

    @GET("animals")
    suspend fun getPets(
        @Header("Authorization") authHeader: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<PetModelResponse>

    @GET("animals/{id}")
    suspend fun getPetDetails(
        @Header("Authorization") authHeader: String,
        @Path(value = "id", encoded = true) id: String
    ): Response<Animal>
}