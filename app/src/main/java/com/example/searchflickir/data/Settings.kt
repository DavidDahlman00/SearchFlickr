package com.example.searchflickir.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings_table")
data class Settings(
    @PrimaryKey val version: Int,
    val numImagesToReturn: String = "20",
    val useUploadDate: Boolean = false,
    val minUploadDate: String = "1970-01-01",
    val useLocation: Boolean = false,
    val latitude: Double = 59.0,
    val longitude: Double = 18.0,
    val radius: String = "20",
    val count: Int

) {
}