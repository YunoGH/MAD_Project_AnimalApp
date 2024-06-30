package com.example.animalApp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LoginDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLogin(loginInfo: LoginInfo)

    @Delete
    suspend fun deleteLogin(loginInfo: LoginInfo)

    @Query("SELECT * FROM login_info")
    suspend fun getAllLogins(): List<LoginInfo>
}
@Dao
interface PetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPetInfo(petInfo: PetInfo)

    @Delete
    suspend fun deletePetInfo(petInfo: PetInfo)

    @Query("DELETE FROM pet_info")
    suspend fun deleteAllPetInfo()

    @Query("SELECT * FROM pet_info")
    suspend fun getAllPetInfo(): List<PetInfo>
}

@Dao
interface AppointmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppointment(appointment: Appointment)

    @Delete
    suspend fun deleteAppointment(appointment: Appointment)

    @Query("DELETE FROM appointments")
    suspend fun deleteAllAppointments()

    @Query("SELECT * FROM appointments")
    suspend fun getAllAppointments(): List<Appointment>
}

@Dao
interface VetInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVetInfo(vetInfo: VetInfo)

    @Delete
    suspend fun deleteVetInfo(vetInfo: VetInfo)

    @Query("DELETE FROM vet_info")
    suspend fun deleteAllVetInfo()

    @Query("SELECT * FROM vet_info")
    suspend fun getAllVetInfo(): List<VetInfo>
}
