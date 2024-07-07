package com.example.animalApp.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// ViewModel for managing application settings
class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    // Shared preferences instance for storing settings
    private val preferences = application.getSharedPreferences("app_settings", Context.MODE_PRIVATE)
    private val editor = preferences.edit()

    // StateFlow to observe dark mode state
    private val _isDarkMode = MutableStateFlow(preferences.getBoolean(KEY_DARK_MODE, false))
    val isDarkMode: StateFlow<Boolean> get() = _isDarkMode

    // Function to toggle visual changes on or off
    fun toggleDarkMode() {
        val newValue = !_isDarkMode.value
        _isDarkMode.value = newValue
        // Persist the new dark mode state in shared preferences
        editor.putBoolean(KEY_DARK_MODE, newValue).apply()
    }

    // Companion object holding constant keys for shared preferences
    companion object {
        private const val KEY_DARK_MODE = "dark_mode"
    }
}
