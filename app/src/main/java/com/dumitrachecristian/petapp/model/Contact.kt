package com.dumitrachecristian.petapp.model

import com.google.gson.annotations.SerializedName

data class Contact(
    @SerializedName("email") val email: String?,
    @SerializedName("phone") val phone: String?,
    @SerializedName("address") val address: Address?
)