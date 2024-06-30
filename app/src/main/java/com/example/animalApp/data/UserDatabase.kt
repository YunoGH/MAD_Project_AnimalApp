package com.example.animalApp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [LoginInfo::class, PetInfo::class, Appointment::class, VetInfo::class], version = 6, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun loginDao(): LoginDao
    abstract fun petDao(): PetDao
    abstract fun appointmentDao(): AppointmentDao
    abstract fun vetInfoDao(): VetInfoDao

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

        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Create the 'pets' table
                database.execSQL("ALTER TABLE user_info ADD COLUMN ownerName TEXT DEFAULT '' NOT NULL")
                database.execSQL("ALTER TABLE user_info ADD COLUMN ownerPassword TEXT DEFAULT '' NOT Null")
            }
        }

        private val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS user_info_new (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        name TEXT NOT NULL,
                        age INTEGER NOT NULL,
                        animalType TEXT NOT NULL,
                        race TEXT,
                        color TEXT NOT NULL,
                        sex TEXT NOT NULL,
                        eyeColor TEXT NOT NULL,
                        dateOfBirth TEXT NOT NULL,
                        photoUri TEXT
                    )
                """.trimIndent())

                // Copy data from the old table to the new table
                database.execSQL("""
                    INSERT INTO user_info_new (id, name, age, animalType, race, color, sex, eyeColor, dateOfBirth, photoUri)
                    SELECT id, name, age, animalType, race, color, sex, eyeColor, dateOfBirth, photoUri FROM user_info
                
                """.trimIndent())

                // Drop the old table
                database.execSQL("DROP TABLE user_info")

                // Rename the new table to the old table's name
                database.execSQL("ALTER TABLE user_info_new RENAME TO user_info")
                //add a new login table
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS login_info (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        ownerName TEXT NOT NULL,
                        ownerPassword TEXT NOT NULL
                    )
                """.trimIndent())
            }
        }

        private val MIGRATION_5_6 = object : Migration(5, 6) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // rename data classes
                database.execSQL("DROP TABLE user_info")
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS pet_info (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        name TEXT NOT NULL,
                        age INTEGER NOT NULL,
                        animalType TEXT NOT NULL,
                        race TEXT,
                        color TEXT NOT NULL,
                        sex TEXT NOT NULL,
                        eyeColor TEXT NOT NULL,
                        dateOfBirth TEXT NOT NULL,
                        photoUri TEXT
                    )
                """.trimIndent())
                database.execSQL("DROP TABLE pets")
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS vet_info (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        animalType TEXT NOT NULL,
                        race TEXT NOT NULL,
                        vaccines TEXT NOT NULL
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
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5, MIGRATION_5_6)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}