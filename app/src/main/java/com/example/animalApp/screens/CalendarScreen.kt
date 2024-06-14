package com.example.animalApp.screens

import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.animalApp.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(navController: NavController) {
    var selectedAppointmentType by remember { mutableStateOf("Vet") }
    var appointmentDate by remember { mutableStateOf("") }
    var appointmentDetails by remember { mutableStateOf("") }

    val appointmentTypes = listOf("Vet", "Leisure")

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
                    label = { Text("Health data") },
                    selected = currentDestination?.hierarchy?.any {
                        it.route == Screen.VetInfoScreen.route
                    } == true,
                    onClick = { navController.navigate(Screen.VetInfoScreen.route) },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Health data"
                        )
                    }
                )
                NavigationBarItem(
                    label = { Text("Appointments") },
                    selected = currentDestination?.hierarchy?.any {
                        it.route == Screen.CalendarScreen.route
                    } == true,
                    onClick = { navController.navigate(Screen.CalendarScreen.route) },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.DateRange,
                            contentDescription = "Appointments"
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

            Text(
                "Add Appointment",
                style = MaterialTheme.typography.headlineMedium
            ) //// arrange size!

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                appointmentTypes.forEach { type ->
                    Button(
                        onClick = { selectedAppointmentType = type },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedAppointmentType == type) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.tertiaryContainer,
                        )
                    ) {
                        Text(type)
                    }
                }
            }

            OutlinedTextField(
                value = appointmentDate,
                onValueChange = { appointmentDate = it },
                label = { Text("Date") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = appointmentDetails,
                onValueChange = { appointmentDetails = it },
                label = { Text("Details") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    // ADD DATABASE with Room to store UserInputs !!!!
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Add Appointment")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Appointments", style = MaterialTheme.typography.headlineMedium)

            // List of appointments (later filled with DB-Data)
            val appointments = listOf(
                "Vet Appointment on 12/06/2023",
                "Leisure Appointment on 15/06/2023"
            )
            appointments.forEach { appointment ->
                Text(appointment, modifier = Modifier.padding(4.dp))
            }

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