package com.example.animalApp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.animalApp.AppointmentForm
import com.example.animalApp.navigation.Screen
import com.example.animalApp.ui.theme.AnimalAppTheme
import com.example.animalApp.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(navController: NavHostController, viewModel: SettingsViewModel = viewModel()) {
    val isDarkMode by viewModel.isDarkMode.collectAsState()

    AnimalAppTheme(darkTheme = isDarkMode) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Appointments") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary
                    )
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
        ) { innerPadding ->
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text =
                    """
                        Let's organize around your schedule! 
                        
                        Add vet appointments, meet-ups with your pet's animal friends or fun activities for you and your pets!
    
                    """.trimIndent(),
                )

                AppointmentForm()

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navController.navigate("home") },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Back to Home")
                }
            }
        }
    }
}
