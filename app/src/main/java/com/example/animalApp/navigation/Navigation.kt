package com.example.animalApp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.animalApp.screens.AddPetScreen
import com.example.animalApp.screens.CalendarScreen
import com.example.animalApp.screens.HomeScreen
import com.example.animalApp.screens.SettingsScreen
import com.example.animalApp.screens.VetInfoScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController,
        startDestination = Screen.HomeScreen.route) {  // pass a start destination
        composable(route = Screen.HomeScreen.route){   // route with name "homeScreen" navigates to HomeScreen composable
            HomeScreen(navController = navController)
        }

        composable(
            route = Screen.AddPetScreen.route){
            AddPetScreen(navController = navController)
        }

        composable(
            route = Screen.VetInfoScreen.route){
            VetInfoScreen(navController = navController)
        }
        composable(
            route = Screen.CalendarScreen.route){

            CalendarScreen(navController = navController)
        }
        composable(
            route = Screen.SettingsScreen.route){
            SettingsScreen(navController = navController)
        }
    }
}
