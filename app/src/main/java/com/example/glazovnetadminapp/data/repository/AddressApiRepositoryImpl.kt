package com.example.glazovnetadminapp.data.repository

import com.example.glazovnetadminapp.data.mappers.toAddressFilterElement
import com.example.glazovnetadminapp.domain.models.announcements.AddressFilterElement
import com.example.glazovnetadminapp.domain.repository.AddressApiRepository
import com.example.glazovnetadminapp.domain.util.Resource
import com.example.glazovnetadminapp.entity.AddressModelDto
import com.example.glazovnetadminapp.entity.ApiResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject
import javax.inject.Named

private const val PATH = "api/addressinfo"

class AddressApiRepositoryImpl @Inject constructor(
    @Named("RestClient") private val client: HttpClient
): AddressApiRepository {
    override suspend fun getStreetsWithName(
        cityName: String,
        streetName: String,
        apiKey: String
    ): Resource<List<String>> {
        return try {
            val response: ApiResponseDto<List<String>> = client.get("$PATH/geetstreetslist") {
                parameter("api_key", apiKey)
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
                message = e.message.toString()
            )
        }
    }

    override suspend fun getCitiesWithName(
        cityName: String,
        apiKey: String
    ): Resource<List<String>> {
        return try {
            val response: ApiResponseDto<List<String>> = client.get("$PATH/getcitieslist") {
                parameter("api_key", apiKey)
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
                message = e.message.toString()
            )
        }
    }

    override suspend fun isAddressExist(
        cityName: String,
        streetName: String,
        houseNumber: String,
        apiKey: String
    ): Resource<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getAddresses(
        cityName: String,
        streetName: String,
        apiKey: String
    ): Resource<List<AddressFilterElement>> {
        return try {
            val response: ApiResponseDto<List<AddressModelDto>> = client.get("$PATH/getaddresses") {
                parameter("api_key", apiKey)
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
            Resource.Error(
                message = e.message.toString()
            )
        }
    }
}