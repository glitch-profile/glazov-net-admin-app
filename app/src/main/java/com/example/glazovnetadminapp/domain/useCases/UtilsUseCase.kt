package com.example.glazovnetadminapp.domain.useCases

import android.content.Context
import com.example.glazovnetadminapp.domain.repository.LocalSettingsRepository
import com.example.glazovnetadminapp.domain.repository.UtilsApiRepository
import com.example.glazovnetadminapp.domain.util.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
class UtilsUseCase @Inject constructor(
    private val utilsApiRepository: UtilsApiRepository,
    private val localSettingsRepository: LocalSettingsRepository
) {

    suspend fun uploadFile(file: File): Resource<List<String>> {
        val apiKey = localSettingsRepository.getSavedApiKey()
        return utilsApiRepository.uploadImage(
            file = file,
            apiKey = apiKey
        )
    }

}

