package com.dumitrachecristian.petapp.model

data class SelectedFilter(
    val animalType: String,
    val gender: String,
    val currentLocationLabel: String,
    val currentLocationValue: String
)
