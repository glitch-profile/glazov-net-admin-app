package com.example.glazovnetadminapp.domain.useCases

import com.example.glazovnetadminapp.domain.repository.LocalSettingsRepository
import com.example.glazovnetadminapp.domain.repository.UtilsApiRepository
import com.example.glazovnetadminapp.domain.util.Resource
import com.example.glazovnetadminapp.entity.AuthDataDto
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
        isRememberToken: Boolean
    ): Resource<String> {
        val authData = AuthDataDto(
            username = login,
            password = password,
            asAdmin = asAdmin
        )
        val loginResult = utilsApiRepository.login(authData)
        if (loginResult is Resource.Success) {
            localSettingsRepository.setLoginToken(
                loginToken = loginResult.data,
                isNeedToSave = isRememberToken
            )
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

