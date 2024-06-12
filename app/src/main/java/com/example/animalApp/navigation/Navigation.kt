package com.example.animalApp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.animalApp.screens.AddPetScreen
import com.example.animalApp.screens.HomeScreen


@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController,
        startDestination = "homescreen") {
        composable(route = "homescreen"){
            HomeScreen(navController = navController)
        }

        composable(
            route = "addpetscreen",
            arguments = listOf(navArgument(name = "movieId") {type = NavType.StringType})
        ) { backStackEntry ->
            AddPetScreen(navController = navController)
        }
    }
}
