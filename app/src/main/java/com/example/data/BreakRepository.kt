package com.example.data

import java.util.Calendar
import kotlinx.coroutines.flow.Flow

class BreakRepository(private val breakDao: BreakDao) {
    fun getBreaksTodayCount(): Flow<Int> {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return breakDao.getBreaksSinceCount(calendar.timeInMillis)
    }

    suspend fun recordBreak() {
        breakDao.insertBreak(BreakEntity())
    }
}
