package com.example.animalApp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.animalApp.R
import com.example.animalApp.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VetInfoScreen(navController: NavController) {
    Scaffold(
        topBar = {
        CenterAlignedTopAppBar(
            title = { Text("Health data") }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary
            )
        )
    }, bottomBar = {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        NavigationBar {
            NavigationBarItem(label = { Text("Home") },
                selected = currentDestination?.hierarchy?.any {
                    it.route == Screen.HomeScreen.route
                } == true,
                onClick = { navController.navigate(Screen.HomeScreen.route) },
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Home, contentDescription = "Go to home"
                    )
                })
            NavigationBarItem(label = { Text("Add Pet") },
                selected = currentDestination?.hierarchy?.any {
                    it.route == Screen.AddPetScreen.route
                } == true,
                onClick = { navController.navigate(Screen.AddPetScreen.route) },
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Add, contentDescription = "Add Pet"
                    )
                })
            NavigationBarItem(label = { Text("Health data") },
                selected = currentDestination?.hierarchy?.any {
                    it.route == Screen.VetInfoScreen.route
                } == true,
                onClick = { navController.navigate(Screen.VetInfoScreen.route) },
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Favorite, contentDescription = "Health data"
                    )
                })
            NavigationBarItem(label = { Text("Appointments") },
                selected = currentDestination?.hierarchy?.any {
                    it.route == Screen.CalendarScreen.route
                } == true,
                onClick = { navController.navigate(Screen.CalendarScreen.route) },
                icon = {
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = "Appointments"
                    )
                })
        }
    }) { innerPadding ->
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
                    Let's add your pet's health data! 
                    
                    This way you will never forget any important vaccination data, medical information or other data of your pet.


                """.trimIndent(),
            )
            Card(
                modifier = Modifier
                    .width(250.dp)
                    .height(350.dp)
                    .padding(10.dp)
            ) {
                Image(
                    modifier = Modifier
                        .padding(10.dp),
                    painter = painterResource(id = R.drawable.hund),
                    contentDescription = "Doggy",
                    contentScale = ContentScale.FillWidth
                )
            }
        }
    }
}