package com.example.glazovnetadminapp.data.repository

import com.example.glazovnetadminapp.data.mappers.toClientModel
import com.example.glazovnetadminapp.data.remote.GlazovNetApi
import com.example.glazovnetadminapp.domain.models.clients.ClientModel
import com.example.glazovnetadminapp.domain.repository.ClientsApiRepository
import com.example.glazovnetadminapp.domain.util.Resource
import com.example.glazovnetadminapp.entity.clientsDto.ClientModelDto
import retrofit2.HttpException
import javax.inject.Inject

class ClientsApiRepositoryImpl @Inject constructor(
    private val api: GlazovNetApi
): ClientsApiRepository {

    //TODO:Change return type of ClientModelDto to ClientModel

    override suspend fun createClient(
        apiKey: String,
        client: ClientModelDto
    ): Resource<ClientModel?> {
        TODO("Not yet implemented")
    }

    override suspend fun getClients(apiKey: String): Resource<List<ClientModel>> {
        return try {
            val result = api.getAllClients(apiKey)
            if (result.status) {
                Resource.Success(
                    data = result.data.map { it.toClientModel() },
                    message = result.message
                )
            } else {
                Resource.Error(message = result.message)
            }
        } catch (e: HttpException) {
            Resource.Error(message = e.code().toString())
        }
    }

}