package com.dumitrachecristian.petapp.model

import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("small") val small: String,
    @SerializedName("medium") val medium: String,
    @SerializedName("large") val large: String,
    @SerializedName("full") val full: String
)