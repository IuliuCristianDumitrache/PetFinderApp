package com.dumitrachecristian.petapp.model

import com.google.gson.annotations.SerializedName

data class AnimalDto(
    @SerializedName("id") val id: Int,
    @SerializedName("url") val url: String,
    @SerializedName("type") val type: String,
    @SerializedName("species") val species: String,
    @SerializedName("breeds") val breeds: Breeds,
    @SerializedName("age") val age: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("size") val size: String? = null,
    @SerializedName("tags") val tags: List<String>? = null,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("photos") val photos: List<Photo>? = null,
    @SerializedName("status") val status: String,
    @SerializedName("contact") val contact: Contact? = null,
    @SerializedName("_links") val links: Links? = null,
    @Transient var isFavorite: Boolean = false
)
