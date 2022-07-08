package com.example.searchflickir.settings

import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import com.example.searchflickir.data.Settings
import com.example.searchflickir.data.SettingsRepository
import kotlinx.coroutines.launch
import kotlin.random.Random

class SettingsViewModel(private val repository: SettingsRepository): ViewModel() {

    val settingsData: LiveData<List<Settings>> = repository.currentSettings.asLiveData()

    fun insert(settings: Settings) = viewModelScope.launch {
        repository.insert(settings)
    }
/*
    init {
        settings = settingsData.value?.get(0) ?: Settings(1, minUploadDate = Random(111).nextInt().toString(), count = 1).also {
            insert(it)
        }
    }

    companion object {
       lateinit var settings: Settings
    }*/
}

class SettingsViewModelFactory(private val repository: SettingsRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}