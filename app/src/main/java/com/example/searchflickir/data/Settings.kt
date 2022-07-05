package com.example.searchflickir.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings_table")
class Settings(
    @PrimaryKey var version: Int = 1,
    var numImagesToReturn: String = "20",
    var useUploadDate: Boolean = false,
    var minUploadDate: String = "1970-01-01",
    var useLocation: Boolean = false,
    var latitude: Double = 59.0,
    var longitude: Double = 18.0,
    var radius: String = "20"
) {
}