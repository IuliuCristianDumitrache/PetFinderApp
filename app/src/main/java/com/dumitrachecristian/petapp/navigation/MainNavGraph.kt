package com.dumitrachecristian.petapp.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.dumitrachecristian.petapp.ui.favoritescreen.FavoriteScreen
import com.dumitrachecristian.petapp.ui.mainscreen.MainScreen
import com.dumitrachecristian.petapp.ui.mainscreen.MainViewModel
import com.dumitrachecristian.petapp.ui.petdetailsscreen.PetDetailsScreen

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
    viewModel: MainViewModel,
) {
    navigation(
        startDestination = Screen.MainScreen.route,
        route = MAIN_ROUTE,
    ) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController, viewModel = viewModel)
        }

        composable(
            route = "${Screen.PetDetailScreen.route}/{id}"
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            PetDetailsScreen(navController = navController, viewModel = viewModel, id = id)
        }

        composable(route = Screen.FavoriteScreen.route) {
            FavoriteScreen(navController = navController, viewModel = viewModel)
        }
    }
}