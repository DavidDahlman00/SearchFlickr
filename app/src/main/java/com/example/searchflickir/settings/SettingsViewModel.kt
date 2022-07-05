package com.example.searchflickir.settings

import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import com.example.searchflickir.data.Settings
import com.example.searchflickir.data.SettingsRepository
import kotlinx.coroutines.launch

class SettingsViewModel(private val repository: SettingsRepository): ViewModel() {

    val settings: LiveData<List<Settings>> = repository.currentSettings as LiveData<List<Settings>>

    fun insert(settings: Settings) = viewModelScope.launch {
        repository.insert(settings)
    }
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