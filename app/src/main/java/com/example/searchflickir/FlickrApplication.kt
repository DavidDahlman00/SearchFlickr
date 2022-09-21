package com.example.searchflickir

import android.app.Application
import android.content.Context
import com.example.searchflickir.data.SettingsRepository
import com.example.searchflickir.data.SettingsRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class FlickrApplication: Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())
    private val database by lazy { SettingsRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { SettingsRepository(database.settingsDao()) }


}