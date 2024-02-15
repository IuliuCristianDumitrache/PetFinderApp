package com.dumitrachecristian.petapp.model

import com.google.gson.annotations.SerializedName

data class Environment(
    @SerializedName("children") val children: Boolean,
    @SerializedName("dogs") val dogs: Boolean,
    @SerializedName("cats") val cats: Boolean
)
