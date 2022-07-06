package com.example.searchflickir.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {

    @Query("SELECT * FROM settings_table ORDER BY version ASC")
    fun getSettings(): Flow<List<Settings>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(settings: Settings)

    @Query("DELETE FROM settings_table")
    suspend fun deleteAll()
}