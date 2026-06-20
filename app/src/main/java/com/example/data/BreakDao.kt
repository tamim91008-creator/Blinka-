package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BreakDao {
    @Query("SELECT COUNT(*) FROM break_logs WHERE timestamp >= :startOfTime")
    fun getBreaksSinceCount(startOfTime: Long): Flow<Int>

    @Insert
    suspend fun insertBreak(log: BreakEntity)
}
