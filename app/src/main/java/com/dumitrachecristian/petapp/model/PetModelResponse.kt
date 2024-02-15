package com.dumitrachecristian.petapp.model

import com.google.gson.annotations.SerializedName

data class PetModelResponse(
    @SerializedName("animals") val pets: List<AnimalDto>,
    @SerializedName("pagination") val pagination: Pagination
)
