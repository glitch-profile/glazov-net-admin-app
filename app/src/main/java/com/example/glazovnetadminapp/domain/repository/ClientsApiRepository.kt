package com.example.glazovnetadminapp.domain.repository

import com.example.glazovnetadminapp.domain.models.clients.ClientModel
import com.example.glazovnetadminapp.domain.util.Resource
import com.example.glazovnetadminapp.entity.clientsDto.ClientModelDto

interface ClientsApiRepository {

    suspend fun createClient(
        apiKey: String,
        newClient: ClientModelDto
    ): Resource<ClientModel?>

    suspend fun getClients(
        apiKey: String
    ): Resource<List<ClientModel>>

}