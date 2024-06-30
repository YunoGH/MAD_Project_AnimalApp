package com.example.animalApp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.animalApp.data.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val loginDao = UserDatabase.getDatabase(application).loginDao()
    private val petDao = UserDatabase.getDatabase(application).petDao()
    private val appointmentDao = UserDatabase.getDatabase(application).appointmentDao()
    private val vetInfoDao = UserDatabase.getDatabase(application).vetInfoDao()

    private val _allLogins = MutableStateFlow<List<LoginInfo>>(emptyList())
    val allLogins: StateFlow<List<LoginInfo>> = _allLogins

    private val _allPetInfo = MutableStateFlow<List<PetInfo>>(emptyList())
    val allPetInfo: StateFlow<List<PetInfo>> = _allPetInfo

    private val _allAppointments = MutableStateFlow<List<Appointment>>(emptyList())
    val allAppointments: StateFlow<List<Appointment>> = _allAppointments

    private val _allVetInfo = MutableStateFlow<List<VetInfo>>(emptyList())
    val allVetInfo: StateFlow<List<VetInfo>> = _allVetInfo

    init {
        viewModelScope.launch {
            refreshData()
        }
    }

    private suspend fun refreshData() {
        try {
            val logins = loginDao.getAllLogins()
            val pets = petDao.getAllPetInfo()
            val appointments = appointmentDao.getAllAppointments()
            val vets = vetInfoDao.getAllVetInfo()

            Log.d("MainViewModel", "Logins: $logins")
            Log.d("MainViewModel", "Pets: $pets")
            Log.d("MainViewModel", "Appointments: $appointments")
            Log.d("MainViewModel", "Vets: $vets")

            _allLogins.value = logins
            _allPetInfo.value = pets
            _allAppointments.value = appointments
            _allVetInfo.value = vets
        } catch (e: Exception) {
            Log.e("MainViewModel", "Error refreshing data: ${e.message}", e)
        }
    }

    fun addLogin(loginInfo: LoginInfo) {
        viewModelScope.launch {
            loginDao.insertLogin(loginInfo)
            refreshData()
        }
    }

    fun deleteLogin(loginInfo: LoginInfo) {
        viewModelScope.launch {
            Log.d("MainViewModel", "Deleting login: $loginInfo")
            loginDao.deleteLogin(loginInfo)
            refreshData()
        }
    }

    fun addPetInfo(petInfo: PetInfo) {
        viewModelScope.launch {
            petDao.insertPetInfo(petInfo)
            refreshData()
        }
    }

    fun deletePetInfo(petInfo: PetInfo) {
        viewModelScope.launch {
            petDao.deletePetInfo(petInfo)
            refreshData()
        }
    }
    // Add methods to delete all data from other tables
    fun deleteAllPetInfo() {
        viewModelScope.launch {
            petDao.deleteAllPetInfo()
            refreshData()
        }
    }

    fun addAppointment(appointment: Appointment) {
        viewModelScope.launch {
            appointmentDao.insertAppointment(appointment)
            refreshData()
        }
    }

    fun deleteAppointment(appointment: Appointment) {
        viewModelScope.launch {
            appointmentDao.deleteAppointment(appointment)
            refreshData()
        }
    }
    fun deleteAllAppointments() {
        viewModelScope.launch {
            appointmentDao.deleteAllAppointments()
            refreshData()
        }
    }

    fun addVetInfo(vetInfo: VetInfo) {
        viewModelScope.launch {
            vetInfoDao.insertVetInfo(vetInfo)
            refreshData()
        }
    }

    fun deleteVetInfo(vetInfo: VetInfo) {
        viewModelScope.launch {
            vetInfoDao.deleteVetInfo(vetInfo)
            refreshData()
        }
    }
    fun deleteAllVetInfo() {
        viewModelScope.launch {
            vetInfoDao.deleteAllVetInfo()
            refreshData()
        }
    }

}
