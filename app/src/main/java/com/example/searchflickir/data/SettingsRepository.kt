package com.example.searchflickir.data

import android.util.Log
import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class SettingsRepository(private val settingsDao: SettingsDao) {

    val currentSettings:Flow<List<Settings>> = settingsDao.getSettings()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(settings: Settings){
        settingsDao.insert(settings)
        Log.d("settings", "inserted")
    }



    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun clearSettings(){
        settingsDao.deleteAll()
    }
}