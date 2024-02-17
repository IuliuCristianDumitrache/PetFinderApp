package com.dumitrachecristian.petapp.model

import com.google.gson.annotations.SerializedName

data class TypeResponse(
    @SerializedName("types") val types: List<Type>
)