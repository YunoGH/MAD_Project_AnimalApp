package com.example.animalApp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.animalApp.R
import com.example.animalApp.navigation.Screen
import com.example.animalApp.ui.theme.AnimalAppTheme
import com.example.animalApp.viewmodel.SettingsViewModel
import com.example.animalApp.viewmodels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: MainViewModel = viewModel(), viewModel1: SettingsViewModel = viewModel()) {
    val isDarkMode by viewModel1.isDarkMode.collectAsState()
    val users by viewModel.allUsers.collectAsState()

    AnimalAppTheme(darkTheme = isDarkMode) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("My Pet") },
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
                    text = """
                        Welcome to your Animal Manager App! 
                        
                        Let's get started by adding your pet's data so we can help you organize everything around your darlings. Just press the Plus-Button below!
                        
                        You're also free to change the appearance of this App in the settings.
                    """.trimIndent(),
                )
                Text("Saved Pets:", style = MaterialTheme.typography.headlineMedium)

                users.forEach { user ->
                    Text("Pets Name: " + user.name)
                    Text("Pets age: " + user.age.toString())
                    Text("Animal Type: " + user.animalType)
                    Text("Race: " + user.race ?: "Unknown")
                    Text("Color: " + user.color)
                    Text("Sex: " + user.sex)
                    Text("Eye Color: " + user.eyeColor)
                    Text("Birthday: " + user.dateOfBirth)
                    Text("Pet Photo: ")
                }
                Card(
                    modifier = Modifier
                        .width(250.dp)
                        .height(350.dp)
                        .padding(10.dp)
                ) {
                    Image(
                        modifier = Modifier.padding(10.dp),
                        painter = painterResource(id = R.drawable.hund),
                        contentDescription = "Doggy",
                        contentScale = ContentScale.FillWidth
                    )
                }
            }
        }
    }
}
