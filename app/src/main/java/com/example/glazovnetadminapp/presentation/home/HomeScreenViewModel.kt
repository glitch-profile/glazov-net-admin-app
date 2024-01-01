package com.example.glazovnetadminapp.presentation.home

import androidx.lifecycle.ViewModel
import com.example.glazovnetadminapp.data.repository.LocalSettingsRepositoryImpl
import com.example.glazovnetadminapp.domain.useCases.UtilsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val localSettingsRepositoryImpl: LocalSettingsRepositoryImpl,
    private val utilsUseCase: UtilsUseCase
): ViewModel() {

    var isLoggedIn: Boolean = true
        private set

    init {
        checkIfNotLoggedIn()
    }

    private fun checkIfNotLoggedIn() {
        isLoggedIn = localSettingsRepositoryImpl.getLoginToken()?.isBlank() ?: true
    }

}