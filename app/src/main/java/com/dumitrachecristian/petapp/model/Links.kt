package com.dumitrachecristian.petapp.model

import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("self") val self: Href,
    @SerializedName("type") val type: Href,
    @SerializedName("organization") val organization: Href
)