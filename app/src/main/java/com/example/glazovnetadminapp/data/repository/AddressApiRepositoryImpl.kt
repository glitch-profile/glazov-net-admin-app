package com.example.glazovnetadminapp.data.repository

import com.example.glazovnetadminapp.data.remote.GlazovNetApi
import com.example.glazovnetadminapp.domain.models.announcements.AddressFilterElement
import com.example.glazovnetadminapp.domain.repository.AddressApiRepository
import com.example.glazovnetadminapp.domain.useCases.toAddressFilterElement
import com.example.glazovnetadminapp.entity.AddressModelDto
import javax.inject.Inject

class AddressApiRepositoryImpl @Inject constructor(
    private val api: GlazovNetApi
): AddressApiRepository {
    override suspend fun getStreetsWithName(
        cityName: String,
        streetName: String,
        apiKey: String
    ): List<String> {
        return try {
            api.getStreetsList(cityName, streetName, apiKey)
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getCitiesWithName(
        cityName: String,
        apiKey: String
    ): List<String> {
        return try {
            api.getCitiesList(cityName, apiKey)
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun isAddressExist(
        cityName: String,
        streetName: String,
        houseNumber: String,
        apiKey: String
    ): Boolean {
        TODO("Not yet implemented")
    }

    //TODO:Rework to resource class
    override suspend fun getAddresses(cityName: String, streetName: String, apiKey: String): List<AddressFilterElement> {
        val addresses = api.getAddresses(cityName, streetName, apiKey)
        return addresses.map {
            it.toAddressFilterElement()
        }.flatten()
    }
}