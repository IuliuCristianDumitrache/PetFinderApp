package com.dumitrachecristian.petapp.navigation

const val ROOT_ROUTE = "root"
const val MAIN_ROUTE = "main"

sealed class Screen(
    val route: String,
) {
    object MainScreen: Screen("main_screen")
    object PetDetailScreen: Screen("pet_detail_screen")
    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}