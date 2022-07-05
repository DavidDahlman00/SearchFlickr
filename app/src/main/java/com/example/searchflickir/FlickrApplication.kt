package com.example.searchflickir

import android.app.Application
import com.example.searchflickir.data.SettingsRepository
import com.example.searchflickir.data.SettingsRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class FlickrApplication: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { SettingsRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { SettingsRepository(database.settingsDao()) }
}