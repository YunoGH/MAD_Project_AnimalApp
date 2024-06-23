package com.example.animalApp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [UserInfo::class, Appointment::class, Pet::class], version = 3, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun appointmentDao(): AppointmentDao
    abstract fun petDao(): PetDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Create the 'pets' table
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS `pets` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `animalType` TEXT NOT NULL,
                        `race` TEXT NOT NULL,
                        `vaccines` TEXT NOT NULL
                    )
                """.trimIndent())
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Create the 'pets' table
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS `appointments` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `appointmentType` TEXT NOT NULL,
                        `date` TEXT NOT NULL,
                        `time` TEXT NOT NULL,
                        `details` TEXT NOT NULL
                    )
                """.trimIndent())
            }
        }

        fun getDatabase(context: Context): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                )
                    //.addMigrations(MIGRATION_1_2)
                    .addMigrations(MIGRATION_2_3)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
