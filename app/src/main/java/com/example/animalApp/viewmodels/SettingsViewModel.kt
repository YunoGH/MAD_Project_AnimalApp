package com.example.animalApp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val preferences = application.getSharedPreferences("app_settings", Context.MODE_PRIVATE)
    private val editor = preferences.edit()

    private val _isDarkMode = MutableStateFlow(preferences.getBoolean(KEY_DARK_MODE, false))
    val isDarkMode: StateFlow<Boolean> get() = _isDarkMode

    fun toggleDarkMode() {
        val newValue = !_isDarkMode.value
        _isDarkMode.value = newValue
        editor.putBoolean(KEY_DARK_MODE, newValue).apply()
    }

    companion object {
        private const val KEY_DARK_MODE = "dark_mode"
    }
}
