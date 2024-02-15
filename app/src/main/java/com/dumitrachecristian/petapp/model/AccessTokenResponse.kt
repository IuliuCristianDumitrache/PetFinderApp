package com.dumitrachecristian.petapp.model

import com.google.gson.annotations.SerializedName

data class AccessTokenResponse(
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("expires_in") val expiresIn: Int,
    @SerializedName("access_token") val accessToken: String
)