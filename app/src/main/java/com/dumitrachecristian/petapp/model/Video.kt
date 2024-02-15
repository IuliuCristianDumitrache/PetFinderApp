package com.dumitrachecristian.petapp.model

import com.google.gson.annotations.SerializedName

data class Video(
    @SerializedName("embed") val embed: String
)
