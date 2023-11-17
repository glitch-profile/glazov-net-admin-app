package com.example.glazovnetadminapp.data.repository

import com.example.glazovnetadminapp.data.remote.GlazovNetApi
import com.example.glazovnetadminapp.domain.models.announcements.AddressFilterElement
import com.example.glazovnetadminapp.domain.repository.AddressApiRepository
import com.example.glazovnetadminapp.data.mappers.toAddressFilterElement
import com.example.glazovnetadminapp.domain.util.Resource
import javax.inject.Inject

class AddressApiRepositoryImpl @Inject constructor(
    private val api: GlazovNetApi
): AddressApiRepository {
    override suspend fun getStreetsWithName(
        cityName: String,
        streetName: String,
        apiKey: String
    ): Resource<List<String>> {
        return try {
            val result = api.getStreetsList(cityName, streetName, apiKey)
            if (result.status) {
                Resource.Success(
                    data = result.data,
                    message = result.message
                )
            } else {
                Resource.Error(
                    message = result.message
                )
            }
        } catch (e: Exception) {
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
            val result = api.getCitiesList(cityName, apiKey)
            if (result.status) {
                Resource.Success(
                    data = result.data,
                    message = result.message
                )
            } else {
                Resource.Error(
                    message = result.message
                )
            }
        } catch (e: Exception) {
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
            val addresses = api.getAddresses(cityName, streetName, apiKey)
            if (addresses.status) {
                Resource.Success(
                    data = addresses.data.map { it.toAddressFilterElement() }.flatten(),
                    message = addresses.message
                )
            } else {
                Resource.Error(
                    message = addresses.message
                )
            }
        } catch (e: Exception) {
            Resource.Error(
                message = e.message.toString()
            )
        }
    }
}