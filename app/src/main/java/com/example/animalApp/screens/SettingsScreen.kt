package com.example.animalApp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.animalApp.navigation.Screen
import com.example.animalApp.ui.theme.AnimalAppTheme
import com.example.animalApp.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController, viewModel: SettingsViewModel = viewModel()) {
    // Collect the current state of isDarkMode from the SettingsViewModel
    val isDarkMode by viewModel.isDarkMode.collectAsState()

    AnimalAppTheme(darkTheme = isDarkMode) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Settings") },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            },
            bottomBar = {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                NavigationBar {
                    NavigationBarItem(
                        label = { Text("Home") },
                        selected = currentDestination?.hierarchy?.any {
                            it.route == Screen.HomeScreen.route
                        } == true,
                        onClick = { navController.navigate(Screen.HomeScreen.route) },
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Home,
                                contentDescription = "Go to home"
                            )
                        }
                    )
                    NavigationBarItem(
                        label = { Text("Add Pet") },
                        selected = currentDestination?.hierarchy?.any {
                            it.route == Screen.AddPetScreen.route
                        } == true,
                        onClick = { navController.navigate(Screen.AddPetScreen.route) },
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Add Pet"
                            )
                        }
                    )
                    NavigationBarItem(
                        label = { Text("Health") },
                        selected = currentDestination?.hierarchy?.any {
                            it.route == Screen.VetInfoScreen.route
                        } == true,
                        onClick = { navController.navigate(Screen.VetInfoScreen.route) },
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = "Health"
                            )
                        }
                    )
                    NavigationBarItem(
                        label = { Text("Schedule") },
                        selected = currentDestination?.hierarchy?.any {
                            it.route == Screen.CalendarScreen.route
                        } == true,
                        onClick = { navController.navigate(Screen.CalendarScreen.route) },
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.DateRange,
                                contentDescription = "Schedule"
                            )
                        }
                    )
                    NavigationBarItem(
                        label = { Text("Settings") },
                        selected = currentDestination?.hierarchy?.any {
                            it.route == Screen.SettingsScreen.route
                        } == true,
                        onClick = { navController.navigate(Screen.SettingsScreen.route) },
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = "Settings"
                            )
                        }
                    )
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp, vertical = 8.dp) // Adding slight margin to all edges
            ) {
                Text(
                    text = "Toggle Dark Mode",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Dark Mode",
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp)) // Adding space between text and switch
                    Switch(
                        checked = isDarkMode,
                        onCheckedChange = { viewModel.toggleDarkMode() },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }
        }

    }
}
