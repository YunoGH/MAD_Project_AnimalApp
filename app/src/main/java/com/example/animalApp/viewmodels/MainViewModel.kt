package com.example.animalApp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.animalApp.data.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// ViewModel class for managing UI-related data in a lifecycle conscious way
class MainViewModel(application: Application) : AndroidViewModel(application) {

    // Data Access Objects for the database
    private val loginDao = UserDatabase.getDatabase(application).loginDao()
    private val petDao = UserDatabase.getDatabase(application).petDao()
    private val appointmentDao = UserDatabase.getDatabase(application).appointmentDao()
    private val vetInfoDao = UserDatabase.getDatabase(application).vetInfoDao()

    // MutableStateFlows for holding the data lists
    private val _allLogins = MutableStateFlow<List<LoginInfo>>(emptyList())
    val allLogins: StateFlow<List<LoginInfo>> = _allLogins

    private val _allPetInfo = MutableStateFlow<List<PetInfo>>(emptyList())
    val allPetInfo: StateFlow<List<PetInfo>> = _allPetInfo

    private val _allAppointments = MutableStateFlow<List<Appointment>>(emptyList())
    val allAppointments: StateFlow<List<Appointment>> = _allAppointments

    private val _allVetInfo = MutableStateFlow<List<VetInfo>>(emptyList())
    val allVetInfo: StateFlow<List<VetInfo>> = _allVetInfo

    // Initializer block to refresh data when ViewModel is created
    init {
        viewModelScope.launch {
            refreshData()
        }
    }

    // Refresh data from the database and update StateFlows
    private suspend fun refreshData() {
        try {
            val logins = loginDao.getAllLogins()
            val pets = petDao.getAllPetInfo()
            val appointments = appointmentDao.getAllAppointments()
            val vets = vetInfoDao.getAllVetInfo()

            _allLogins.value = logins
            _allPetInfo.value = pets
            _allAppointments.value = appointments
            _allVetInfo.value = vets
        } catch (e: Exception) {
            Log.e("MainViewModel", "Error refreshing data: ${e.message}", e)
        }
    }

    // Functions to add and delete data, either singular data or the whole drop the whole table content
    fun addLogin(loginInfo: LoginInfo) {
        viewModelScope.launch {
            loginDao.insertLogin(loginInfo)
            refreshData()
        }
    }

    fun deleteLogin(loginInfo: LoginInfo) {
        viewModelScope.launch {
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
