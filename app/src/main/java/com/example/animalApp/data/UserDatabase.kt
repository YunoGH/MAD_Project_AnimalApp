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
                    CREATE TABLE IF NOT EXISTS pets (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        animalType TEXT NOT NULL,
                        race TEXT NOT NULL,
                        vaccines TEXT NOT NULL
                    )
                """.trimIndent())
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Create a new table with the expected schema
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS appointments_new (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        appointmentType TEXT NOT NULL,
                        date TEXT NOT NULL,
                        time TEXT NOT NULL,
                        details TEXT NOT NULL
                    )
                """.trimIndent())

                // Copy data from the old table to the new table, setting default values for new columns
                database.execSQL("""
                    INSERT INTO appointments_new (id, appointmentType, date, details, time)
                    SELECT id, appointmentType, date, details, '' as time FROM appointments
                """.trimIndent())

                // Drop the old table
                database.execSQL("DROP TABLE appointments")

                // Rename the new table to the old table's name
                database.execSQL("ALTER TABLE appointments_new RENAME TO appointments")
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
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}