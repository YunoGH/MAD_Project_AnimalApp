package com.example.animalApp.data

import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userInfo: UserInfo)

    @Query("SELECT * FROM user_info")
    suspend fun getAllUsers(): List<UserInfo>
}

@Dao
interface AppointmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppointment(appointment: Appointment)

    @Query("SELECT * FROM appointments")
    suspend fun getAllAppointments(): List<Appointment>
}
