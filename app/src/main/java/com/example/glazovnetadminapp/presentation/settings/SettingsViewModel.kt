package com.example.glazovnetadminapp.presentation.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.glazovnetadminapp.data.repository.LocalSettingsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val localSettingsRepositoryImpl: LocalSettingsRepositoryImpl
): ViewModel() {

    private var _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    private var _apiKey = MutableStateFlow("")
    val apiKey = _apiKey.asStateFlow()

    init {
        loadApiKey()
    }
    fun loadApiKey() {
        _apiKey.update { localSettingsRepositoryImpl.getSavedApiKey() }
    }
    fun updateApiKeyString(newApiKey: String) {
        _apiKey.update { newApiKey }
    }
    fun saveApiKey() {
        viewModelScope.launch {
            localSettingsRepositoryImpl.setSavedApiKey(apiKey.value)
        }
    }
}