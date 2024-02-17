package com.dumitrachecristian.petapp.model

import com.google.gson.annotations.SerializedName

data class AnimalDto(
    @SerializedName("id") val id: Int,
    @SerializedName("organization_id") val organizationId: String,
    @SerializedName("url") val url: String,
    @SerializedName("type") val type: String,
    @SerializedName("species") val species: String,
    @SerializedName("breeds") val breeds: Breeds,
    @SerializedName("colors") val colors: Colors,
    @SerializedName("age") val age: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("size") val size: String,
    @SerializedName("coat") val coat: String?,
    @SerializedName("attributes") val attributes: Attributes,
    @SerializedName("environment") val environment: Environment,
    @SerializedName("tags") val tags: List<String>? = null,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("photos") val photos: List<Photo>,
    @SerializedName("videos") val videos: List<Video>,
    @SerializedName("status") val status: String,
    @SerializedName("published_at") val publishedAt: String,
    @SerializedName("contact") val contact: Contact,
    @SerializedName("_links") val links: Links
)
