package com.example.glazovnetadminapp.domain.useCases

import com.example.glazovnetadminapp.data.repository.ClientsApiRepositoryImpl
import com.example.glazovnetadminapp.data.repository.LocalSettingsRepositoryImpl
import javax.inject.Inject

class ClientsUseCase @Inject constructor(
    private val localSettingsRepository: LocalSettingsRepositoryImpl,
    private val clientsApiRepository: ClientsApiRepositoryImpl
) {

}