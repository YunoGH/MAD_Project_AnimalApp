package com.example.animalApp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.mindrot.jbcrypt.BCrypt
import java.security.SecureRandom

@Entity(tableName = "login_info")
data class LoginInfo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val ownerName: String,
    val hashedPassword: String,
    val salt: String
) {
    companion object {
        // Generates a salt
        fun generateSalt(): String {
            val random = SecureRandom()
            val salt = ByteArray(16)
            random.nextBytes(salt)
            return BCrypt.gensalt()
        }

        // Hashes a password with a given salt
        fun hashPassword(password: String, salt: String): String {
            return BCrypt.hashpw(password, salt)
        }
    }
}



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
