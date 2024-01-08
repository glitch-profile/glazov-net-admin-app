package com.example.glazovnetadminapp.domain.useCases

import com.example.glazovnetadminapp.domain.repository.LocalSettingsRepository
import com.example.glazovnetadminapp.domain.repository.UtilsApiRepository
import com.example.glazovnetadminapp.domain.util.Resource
import com.example.glazovnetadminapp.entity.authDto.AuthDataDto
import com.example.glazovnetadminapp.entity.authDto.AuthResponse
import java.io.File
import javax.inject.Inject

class UtilsUseCase @Inject constructor(
    private val utilsApiRepository: UtilsApiRepository,
    private val localSettingsRepository: LocalSettingsRepository
) {

    suspend fun login(
        login: String,
        password: String,
        asAdmin: Boolean,
        isRememberAuthData: Boolean
    ): Resource<AuthResponse> {
        val authData = AuthDataDto(
            username = login,
            password = password,
            asAdmin = asAdmin
        )
        val loginResult = utilsApiRepository.login(authData)
        if (loginResult is Resource.Success) {
            val authResponse = loginResult.data!!
            localSettingsRepository.setLoginToken(authResponse.token, isRememberAuthData)
            localSettingsRepository.setAssociatedUserId(authResponse.userId, isRememberAuthData)
            localSettingsRepository.setIsUserAsAdmin(authResponse.isAdmin, isRememberAuthData)
            localSettingsRepository.setSavedUserLogin(login)
        }
        return loginResult
    }

    suspend fun uploadFile(file: File): Resource<List<String>> {
        val token = localSettingsRepository.getLoginToken() ?: ""
        return utilsApiRepository.uploadImage(
            file = file,
            token = token
        )
    }

}

