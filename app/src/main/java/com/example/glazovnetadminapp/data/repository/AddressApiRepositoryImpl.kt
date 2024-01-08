package com.example.glazovnetadminapp.data.repository

import com.example.glazovnetadminapp.data.mappers.toAddressFilterElement
import com.example.glazovnetadminapp.domain.models.announcements.AddressFilterElement
import com.example.glazovnetadminapp.domain.repository.AddressApiRepository
import com.example.glazovnetadminapp.domain.util.Resource
import com.example.glazovnetadminapp.entity.AddressModelDto
import com.example.glazovnetadminapp.entity.ApiResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject
import javax.inject.Named

private const val PATH = "api/address-info"

class AddressApiRepositoryImpl @Inject constructor(
    @Named("RestClient") private val client: HttpClient
): AddressApiRepository {
    override suspend fun getStreetsWithName(
        cityName: String,
        streetName: String,
        token: String
    ): Resource<List<String>> {
        return try {
            val response: ApiResponseDto<List<String>> = client.get("$PATH/streets-list") {
                bearerAuth(token)
                parameter("city", cityName)
                parameter("street", streetName)
            }.body()
            if (response.status) {
                Resource.Success(
                    data = response.data,
                    message = response.message
                )
            } else {
                Resource.Error(
                    message = response.message
                )
            }
        } catch (e: ResponseException) {
            Resource.Error(
                message = e.response.status.toString()
            )
        } catch (e: Exception) {
            Resource.Error(message = e.message ?: "unknown error")
        }
    }

    override suspend fun getCitiesWithName(
        cityName: String,
        token: String
    ): Resource<List<String>> {
        return try {
            val response: ApiResponseDto<List<String>> = client.get("$PATH/cities-list") {
                bearerAuth(token)
                parameter("city", cityName)
            }.body()
            if (response.status) {
                Resource.Success(
                    data = response.data,
                    message = response.message
                )
            } else {
                Resource.Error(
                    message = response.message
                )
            }
        } catch (e: ResponseException) {
            Resource.Error(
                message = e.response.status.toString()
            )
        } catch (e: Exception) {
            Resource.Error(message = e.message ?: "unknown error")
        }
    }

    override suspend fun isAddressExist(
        cityName: String,
        streetName: String,
        houseNumber: String,
        token: String
    ): Resource<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getAddresses(
        cityName: String,
        streetName: String,
        token: String
    ): Resource<List<AddressFilterElement>> {
        return try {
            val response: ApiResponseDto<List<AddressModelDto>> = client.get("$PATH/addresses") {
                bearerAuth(token)
                parameter("city", cityName)
                parameter("street", streetName)
            }.body()
            if (response.status) {
                Resource.Success(
                    data = response.data.map { it.toAddressFilterElement() }.flatten(),
                    message = response.message
                )
            } else {
                Resource.Error(
                    message = response.message
                )
            }
        } catch (e: ResponseException) {
            Resource.Error(message = e.response.status.toString())
        } catch (e: ConnectTimeoutException) {
            Resource.Error(message = "server not available")
        } catch (e: Exception) {
            Resource.Error(message = e.message ?: "unknown error")
        }
    }
}