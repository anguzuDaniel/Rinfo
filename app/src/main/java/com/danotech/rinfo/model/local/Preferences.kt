package com.danotech.rinfo.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "preferences")
data class AppPreferences(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val darkModeEnabled: Boolean = false,
    val notification: Boolean = false,
)
