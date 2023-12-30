package com.example.glazovnetadminapp.domain.useCases

import com.example.glazovnetadminapp.domain.repository.LocalSettingsRepository
import com.example.glazovnetadminapp.domain.repository.UtilsApiRepository
import com.example.glazovnetadminapp.domain.util.Resource
import java.io.File
import javax.inject.Inject

class UtilsUseCase @Inject constructor(
    private val utilsApiRepository: UtilsApiRepository,
    private val localSettingsRepository: LocalSettingsRepository
) {

    suspend fun uploadFile(file: File): Resource<List<String>> {
        val token = localSettingsRepository.getLoginToken() ?: ""
        return utilsApiRepository.uploadImage(
            file = file,
            token = token
        )
    }

}

