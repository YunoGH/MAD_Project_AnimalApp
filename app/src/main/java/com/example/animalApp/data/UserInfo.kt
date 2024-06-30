package com.example.animalApp.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "login_info")
data class LoginInfo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val ownerName: String,
    val ownerPassword: String
)

@Entity(tableName = "pet_info")
data class PetInfo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val age: Int,
    val animalType: String,
    val race: String?,
    val color: String,
    val sex: String,
    val eyeColor: String,
    val dateOfBirth: String,
    val photoUri: String?
)

@Entity(tableName = "appointments")
data class Appointment(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val appointmentType: String,
    val date: String,
    val details: String,
    val time: String

)

@Entity(tableName = "vet_info")
data class VetInfo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val animalType: String,
    val race: String,
    val vaccines: String
)

