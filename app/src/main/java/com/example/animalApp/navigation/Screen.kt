package com.example.animalApp.navigation

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home")
    object AddPetScreen : Screen("addpet")
    object VetInfoScreen : Screen("vetinfo")
    object CalendarScreen : Screen("calendar")
    object SettingsScreen : Screen("settings")

}