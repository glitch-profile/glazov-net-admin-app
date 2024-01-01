package com.example.glazovnetadminapp.presentation.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.glazovnetadminapp.domain.repository.LocalSettingsRepository
import com.example.glazovnetadminapp.domain.useCases.UtilsUseCase
import com.example.glazovnetadminapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val localSettingsRepository: LocalSettingsRepository,
    private val utilsUseCase: UtilsUseCase
): ViewModel() {

    private var _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    private val _loginToken = MutableStateFlow("")
    val loginToken = _loginToken.asStateFlow()
    private val _userLogin = MutableStateFlow("")
    val userLogin = _userLogin.asStateFlow()
    private val _userPassword = MutableStateFlow("")
    val userPassword = _userPassword.asStateFlow()
    private val _isRememberToken = MutableStateFlow(true)
    val isRememberToken = _isRememberToken.asStateFlow()

    init {
        loadDataFromPreferences()
    }
    fun loadDataFromPreferences() {
        with(localSettingsRepository) {
            _loginToken.update { getLoginToken() ?: "" }
            _userLogin.update { getSavedUserLogin() ?: "" }
        }
    }
    fun updateUserLoginString(login: String) {
        _userLogin.update { login }
    }
    fun updateUserPasswordString(password: String) {
        _userPassword.update { password }
    }
    fun checkShouldRememberAuth() {
        _isRememberToken.update { !isRememberToken.value }
    }

    fun login(
        isAdmin: Boolean
    ) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    message = null
                )
            }
            val result = utilsUseCase.login(
                login = userLogin.value,
                password =  userPassword.value,
                asAdmin = isAdmin,
                isRememberToken = isRememberToken.value
            )
            if (result is Resource.Success) {
                _loginToken.update { result.data!! }
            }
            Log.i("AUTH", "login result - ${result.message}")
            _state.update {
                it.copy(
                    isLoading = false,
                    message = result.message
                )
            }
        }
    }
}