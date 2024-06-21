package com.example.animalApp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.animalApp.data.Appointment
import com.example.animalApp.data.UserDatabase
import com.example.animalApp.data.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao = UserDatabase.getDatabase(application).userDao()
    private val appointmentDao = UserDatabase.getDatabase(application).appointmentDao()

    private val _allUsers = MutableStateFlow<List<UserInfo>>(emptyList())
    val allUsers: StateFlow<List<UserInfo>> = _allUsers

    private val _allAppointments = MutableStateFlow<List<Appointment>>(emptyList())
    val allAppointments: StateFlow<List<Appointment>> = _allAppointments

    init {
        viewModelScope.launch {
            _allUsers.value = userDao.getAllUsers()
            _allAppointments.value = appointmentDao.getAllAppointments()
        }
    }

    fun addUser(userInfo: UserInfo) {
        viewModelScope.launch {
            userDao.insertUser(userInfo)
            _allUsers.value = userDao.getAllUsers()
        }
    }

    fun addAppointment(appointment: Appointment) {
        viewModelScope.launch {
            appointmentDao.insertAppointment(appointment)
            _allAppointments.value = appointmentDao.getAllAppointments()
        }
    }

    //suspend fun getAllUsers() = userDao.getAllUsers()

    //suspend fun getAllAppointments() = appointmentDao.getAllAppointments()
}
