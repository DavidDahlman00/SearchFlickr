package com.example.searchflickir.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class SettingsRepository(private val settingsDao: SettingsDao) {

    val currentSettings: Flow<List<Settings>> = settingsDao.getSettings()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(settings: Settings){
        settingsDao.insert(settings)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun clearSettings(){
        settingsDao.deleteAll()
    }
}