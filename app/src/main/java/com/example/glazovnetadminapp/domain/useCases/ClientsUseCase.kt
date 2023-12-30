package com.example.glazovnetadminapp.domain.useCases

import com.example.glazovnetadminapp.data.mappers.toClientModelDto
import com.example.glazovnetadminapp.domain.models.clients.ClientModel
import com.example.glazovnetadminapp.domain.repository.ClientsApiRepository
import com.example.glazovnetadminapp.domain.repository.LocalSettingsRepository
import com.example.glazovnetadminapp.domain.util.Resource
import javax.inject.Inject

class ClientsUseCase @Inject constructor(
    private val localSettingsRepository: LocalSettingsRepository,
    private val clientsApiRepository: ClientsApiRepository
) {

    suspend fun getAllClients(): Resource<List<ClientModel>> {
        val token = localSettingsRepository.getLoginToken() ?: ""
        return clientsApiRepository.getClients(token)
    }

    suspend fun addNewClient(
        clientModel: ClientModel
    ): Resource<ClientModel?> {
        val token = localSettingsRepository.getLoginToken() ?: ""
        return clientsApiRepository.createClient(
            token = token,
            newClient = clientModel.toClientModelDto()
        )
    }

}