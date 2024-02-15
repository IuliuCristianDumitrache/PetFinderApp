package com.dumitrachecristian.petapp.model


data class Animal(
    val id: Int,
    val type: String,
    val species: String,
    val breeds: Breeds,
    val age: String,
    val gender: String,
    val name: String,
    val description: String,
    val status: String,
)