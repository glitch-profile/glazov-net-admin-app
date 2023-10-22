package com.example.glazovnetadminapp.presentation.home

import androidx.lifecycle.ViewModel
import com.example.glazovnetadminapp.data.repository.LocalSettingsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val localSettingsRepositoryImpl: LocalSettingsRepositoryImpl
): ViewModel() {

    var isApiKeyEmpty: Boolean = true
        private set

    init {
        checkIfApiKeyEmpty()
    }

    fun checkIfApiKeyEmpty() {
        isApiKeyEmpty = localSettingsRepositoryImpl.getSavedApiKey().isBlank()
    }

}