package com.example.animalApp

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.animalApp.viewmodels.MainViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.animalApp.data.UserInfo

@Composable
fun UserForm(viewModel: MainViewModel = viewModel()) {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var animalType by remember { mutableStateOf("") }
    var race by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }
    var sex by remember { mutableStateOf("") }
    var eyeColor by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    var photoUri by remember { mutableStateOf<Uri?>(null)}

    val users by viewModel.allUsers.collectAsState()

    // Image launcher
    val imgLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            photoUri = uri
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Enter your details:")

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Age") },
            modifier = Modifier.fillMaxWidth()
            //keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
        )

        OutlinedTextField(
            value = animalType,
            onValueChange = { animalType = it },
            label = { Text("Animal Type") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = race,
            onValueChange = { race = it },
            label = { Text("Race (Optional)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = color,
            onValueChange = { color = it },
            label = { Text("Color") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = sex,
            onValueChange = { sex = it },
            label = { Text("Sex") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = eyeColor,
            onValueChange = { eyeColor = it },
            label = { Text("Eye Color") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = dateOfBirth,
            onValueChange = { dateOfBirth = it },
            label = { Text("Date of Birth") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                imgLauncher.launch("image/*")
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Select Pet Photo")
        }
        photoUri?.let { uri ->
            Image(
                painter = rememberImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(uri)
                        .transformations(CircleCropTransformation())
                        .build()
                ),
                contentDescription = "Pet Photo",
                modifier = Modifier
                    .size(128.dp)
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                      val user = UserInfo(
                          name = name,
                          age = age.toIntOrNull() ?: 0,
                          animalType = animalType,
                          race = race.takeIf { it.isNotEmpty() },
                          color = color,
                          sex = sex,
                          eyeColor = eyeColor,
                          dateOfBirth = dateOfBirth,
                          photoUri = photoUri?.toString()
                      )
                viewModel.addUser(user)
                // Handle form submission here
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Submit")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Display saved users
        Text("Saved Users:", style = MaterialTheme.typography.headlineMedium)
        users.forEach {user ->
            Text(user.name)
        }
    }
}