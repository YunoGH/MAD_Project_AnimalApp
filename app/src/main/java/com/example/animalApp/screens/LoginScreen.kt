package com.example.animalApp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.animalApp.ui.theme.AnimalAppTheme
import com.example.animalApp.viewmodel.SettingsViewModel
import com.example.animalApp.viewmodels.MainViewModel
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.TextButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.example.animalApp.data.LoginInfo
import com.example.animalApp.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel(),
    viewModel1: SettingsViewModel = viewModel()
) {
    val isDarkMode by viewModel1.isDarkMode.collectAsState()
    var ownerName by remember { mutableStateOf("") }
    var ownerPassword by remember { mutableStateOf("") }
    var snackbarShown by remember { mutableStateOf(false) }

    val logins by viewModel.allLogins.collectAsState()

    AnimalAppTheme(darkTheme = isDarkMode) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Welcome") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary
                    ),
                    actions = {
                        Box(contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = Icons.Filled.AccountCircle,
                                    contentDescription = "Account",
                                    Modifier.padding(top = 12.dp, bottom = 0.dp, end = 16.dp))

                                logins.forEach { login ->
                                    Text(text = login.ownerName,
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(bottom = 9.dp, top = 0.dp, end = 16.dp))
                                }
                            }
                        }
                    }
                )
            },
            bottomBar = {
                BottomAppBar {
                    Spacer(modifier = Modifier.weight(1f)) // Push the button to the right
                    if (logins.isNotEmpty()) {
                        Button(
                            onClick = {
                                logins.forEach { login ->
                                    val loginToDelete = logins.firstOrNull {
                                        it.ownerName == login.ownerName && it.ownerPassword == login.ownerPassword
                                    }
                                    loginToDelete?.let { viewModel.deleteLogin(it) }
                                }
                                viewModel.deleteAllPetInfo()
                                viewModel.deleteAllPetInfo()
                                viewModel.deleteAllAppointments()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Remove User")
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (logins.isEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Welcome to your Animal Manager App!",
                        style = MaterialTheme.typography.displaySmall,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Please Login with your Username and Password.",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Enter your Username and Password:")

                    OutlinedTextField(
                        value = ownerName,
                        onValueChange = { ownerName = it },
                        label = { Text("Username") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = ownerPassword,
                        onValueChange = { ownerPassword = it },
                        label = { Text("Password") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = {
                            if (ownerName.isNotEmpty() && ownerPassword.isNotEmpty()) {
                                val login = LoginInfo(
                                    ownerName = ownerName,
                                    ownerPassword = ownerPassword
                                )
                                viewModel.addLogin(login)
                                navController.navigate(Screen.HomeScreen.route)
                            } else {
                                snackbarShown = true
                            }
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text("Login")
                    }
                } else {
                    Text(
                        text = "You are logged in as:",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    logins.forEach { login ->
                        Text(
                            text = login.ownerName,
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                    Button(
                        onClick = { navController.navigate(Screen.HomeScreen.route) },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text("Continue")
                    }

                    Spacer(modifier = Modifier.weight(1f)) // Pushes content to the top
                }

                // Manually manage Snackbar visibility
                if (snackbarShown) {
                    Snackbar(
                        action = {
                            TextButton(onClick = { snackbarShown = false }) {
                                Text("Dismiss")
                            }
                        },
                    ) {
                        Text("Username and Password are required")
                    }
                }
            }
        }
    }
}