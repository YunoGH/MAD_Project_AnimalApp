package com.example.animalApp

import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.animalApp.data.VetInfo
import com.example.animalApp.viewmodels.MainViewModel
import java.util.Calendar

@Composable
fun HealthDataForm(viewModel: MainViewModel = viewModel()) {
    var birthDate by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedVaccine by remember { mutableStateOf("") }
    val vaccines = listOf("Rabies", "Parvovirus", "Distemper", "Hepatitis")
    var animalType by remember { mutableStateOf("") }
    var race by remember { mutableStateOf("") }
    val vetInfo by viewModel.allVetInfo.collectAsState()

    // Date picker dialog
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = android.app.DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            birthDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
        }, year, month, day
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Enter your details:")

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

        // Date of Birth Picker
        Button(
            onClick = { datePickerDialog.show() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(birthDate.ifEmpty { "Select Date of Birth" })
        }

        // Vaccines Dropdown Menu
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.TopStart)
        ) {
            OutlinedTextField(
                value = selectedVaccine,
                onValueChange = { },
                label = { Text("Vaccines") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = null
                        )
                    }
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                vaccines.forEach { vaccine ->
                    DropdownMenuItem(text = { Text(text = vaccine) }, onClick = { selectedVaccine = vaccine
                    expanded = false})
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val vetInfo = VetInfo(
                    animalType = animalType,
                    race = race,
                    vaccines = selectedVaccine
                )
                viewModel.addVetInfo(vetInfo)
                // Handle form submission here
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Submit")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Display saved pet
        Text("Saved Pets", style = MaterialTheme.typography.headlineMedium)

        vetInfo.forEach { vetInfo ->
            Text("${vetInfo.animalType} - ${vetInfo.race}: ${vetInfo.vaccines}")
        }
    }
}