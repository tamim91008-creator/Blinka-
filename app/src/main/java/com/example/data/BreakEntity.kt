package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "break_logs")
data class BreakEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)
