package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.data.AppDatabase
import com.example.data.BreakRepository
import com.example.data.PreferencesRepository
import com.example.service.ReminderManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java, "blinka-db"
    ).build()

    private val breakRepository = BreakRepository(db.breakDao())
    private val prefsRepository = PreferencesRepository(application)
    private val reminderManager = ReminderManager(application)

    val isReminderEnabled: StateFlow<Boolean> = prefsRepository.isReminderEnabled
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    val breaksTodayCount: StateFlow<Int> = breakRepository.getBreaksTodayCount()
        .stateIn(viewModelScope, SharingStarted.Lazily, 0)

    fun setReminderEnabled(enabled: Boolean) {
        viewModelScope.launch {
            prefsRepository.setReminderEnabled(enabled)
            if (enabled) {
                reminderManager.scheduleNextReminder()
            } else {
                reminderManager.cancelReminder()
            }
        }
    }

    fun recordBreak() {
        viewModelScope.launch {
            breakRepository.recordBreak()
        }
    }
}
