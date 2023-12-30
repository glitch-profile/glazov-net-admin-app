package com.example.glazovnetadminapp.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.glazovnetadminapp.domain.repository.LocalSettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val localSettingsRepository: LocalSettingsRepository
): ViewModel() {

    private var _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    private var _apiKey = MutableStateFlow("")
    val apiKey = _apiKey.asStateFlow()

    private val _memberId = MutableStateFlow("")
    val memberId = _memberId.asStateFlow()

    init {
        loadDataFromPreferences()
    }
    fun loadDataFromPreferences() {
        with(localSettingsRepository) {
            _apiKey.update { getSavedApiKey() ?: "" }
            _memberId.update { getLoginToken() ?: "" }
        }
    }
    fun updateApiKeyString(newApiKey: String) {
        _apiKey.update { newApiKey }
    }
    fun updateMemberIdString(newMemberId: String) {
        _memberId.update { newMemberId }
    }

    fun saveDataToPreferences() {
        viewModelScope.launch {
            with(localSettingsRepository) {
                setSavedApiKey(apiKey.value)
                setLoginToken(memberId.value, isNeedToSave = false)
            }
        }
    }
}