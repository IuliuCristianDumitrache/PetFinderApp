package com.dumitrachecristian.petapp.model

import com.google.gson.annotations.SerializedName

data class PaginationLinks(
    @SerializedName("previous") val previous: Href,
    @SerializedName("next") val next: Href
)
