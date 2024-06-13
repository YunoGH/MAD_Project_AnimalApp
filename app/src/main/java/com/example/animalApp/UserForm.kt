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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation

@Composable
fun UserForm(brush: Brush) {
    var name by remember { mutableStateOf(TextFieldValue()) }
    var age by remember { mutableStateOf(TextFieldValue()) }
    var animalType by remember { mutableStateOf(TextFieldValue()) }
    var race by remember { mutableStateOf(TextFieldValue()) }
    var color by remember { mutableStateOf(TextFieldValue()) }
    var sex by remember { mutableStateOf(TextFieldValue()) }
    var eyeColor by remember { mutableStateOf(TextFieldValue()) }
    var photoUri by remember { mutableStateOf<Uri?>(null)}

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
                // Handle form submission here
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Submit")
        }
    }
}