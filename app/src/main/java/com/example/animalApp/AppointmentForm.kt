package com.example.animalApp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.dp
import com.example.animalApp.viewmodels.MainViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.animalApp.data.Appointment

@Composable
fun AppointmentForm(viewModel: MainViewModel = viewModel()) {
    var selectedAppointmentType by remember { mutableStateOf("Vet") }
    var appointmentDate by remember { mutableStateOf("") }
    var appointmentDetails by remember { mutableStateOf("") }

    val appointmentTypes = listOf("Vet", "Leisure")
    val appointments by viewModel.allAppointments.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Add Appointment", style = MaterialTheme.typography.headlineMedium)

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
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
                val appointment = Appointment(
                    appointmentType = selectedAppointmentType,
                    date = appointmentDate,
                    details = appointmentDetails
                )
                viewModel.addAppointment(appointment)
                // Handle form submission here
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Add Appointment")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Display saved appointments
        Text("Appointments", style = MaterialTheme.typography.headlineMedium)

        appointments.forEach { appointment ->
            Text("${appointment.appointmentType} - ${appointment.date}: ${appointment.details}")
        }
    }
}