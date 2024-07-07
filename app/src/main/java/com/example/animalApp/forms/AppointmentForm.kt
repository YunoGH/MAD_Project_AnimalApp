package com.example.animalApp.forms

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.animalApp.NotificationReceiver
import com.example.animalApp.data.Appointment
import com.example.animalApp.viewmodels.MainViewModel
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun AppointmentForm(viewModel: MainViewModel = viewModel()) {

    CheckNotificationPermission()

    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    var pickedTime by remember {
        mutableStateOf(LocalTime.NOON)
    }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("dd/MM/yyyy")
                .format(pickedDate)
        }
    }
    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("HH:mm")
                .format(pickedTime)
        }
    }

    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()

    val context = LocalContext.current


    var selectedAppointmentType by remember { mutableStateOf("Vet") }
    var appointmentDate by remember { mutableStateOf("") }
    var appointmentTime by remember { mutableStateOf("") }
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
            label = { Text(text = formattedDate) },
            interactionSource = remember { MutableInteractionSource() }
                .also { interactionSource ->
                    LaunchedEffect(interactionSource) {
                        interactionSource.interactions.collect {
                            if (it is PressInteraction.Release) {
                                dateDialogState.show()
                            }
                        }
                    }
                },
            modifier = Modifier
                .fillMaxWidth()
        )

        OutlinedTextField(
            value = appointmentTime,
            onValueChange = { appointmentTime = it },
            label = { Text(text = formattedTime) },
            interactionSource = remember { MutableInteractionSource() }
                .also { interactionSource ->
                    LaunchedEffect(interactionSource) {
                        interactionSource.interactions.collect {
                            if (it is PressInteraction.Release) {
                                timeDialogState.show()
                            }
                        }
                    }
                },
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
                    date = formattedDate,
                    time = formattedTime,
                    details = appointmentDetails
                )
                viewModel.addAppointment(appointment)
                // Handle form submission here

                scheduleNotification(
                    context = context,
                    appointmentType = selectedAppointmentType,
                    date = pickedDate,
                    time = pickedTime,
                    details = appointmentDetails
                )

            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Add Appointment")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Display saved appointments
        Text("Appointments", style = MaterialTheme.typography.headlineMedium)

        appointments.forEach { appointment ->
            Text("${appointment.appointmentType} - ${appointment.date}(${appointment.time}): ${appointment.details}")
        }
    }

    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(text = "Ok")
            negativeButton(text = "Cancel")
        }
    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = "Pick a date"
        ) {
            pickedDate = it
        }
    }

    MaterialDialog(
        dialogState = timeDialogState,
        buttons = {
            positiveButton(text = "Ok")
            negativeButton(text = "Cancel")
        }
    ) {
        timepicker(
            initialTime = LocalTime.NOON,
            title = "Pick a date",
            is24HourClock = true
        ) {
            pickedTime = it
        }
    }
}

@SuppressLint("ScheduleExactAlarm")
fun scheduleNotification(context: Context, appointmentType: String, date: LocalDate, time: LocalTime, details: String) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    val appointmentTime = date.atTime(time)
    val notificationTime = appointmentTime.minusHours(1)
    val notificationTimeInMillis = notificationTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

    val intent = Intent(context, NotificationReceiver::class.java).apply {
        putExtra("title", "$appointmentType Appointment")
        putExtra("message", "Details: $details - in one hour")
    }
    val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

    alarmManager.setExact(AlarmManager.RTC_WAKEUP, notificationTimeInMillis, pendingIntent)
}

@Composable
fun CheckNotificationPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13 and above
        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted ->
                if (!isGranted) {
                    // Handle the case when the permission is not granted
                }
            }
        )

        LaunchedEffect(Unit) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}
