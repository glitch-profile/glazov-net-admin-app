package com.example.glazovnetadminapp.data.repository

import com.example.glazovnetadminapp.data.remote.GlazovNetApi
import com.example.glazovnetadminapp.domain.repository.AddressApiRepository
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
        return api.getStreetsList(cityName, streetName, apiKey)
    }

    override suspend fun getCitiesWithName(
        cityName: String,
        apiKey: String
    ): List<String> {
        return api.getCitiesList(cityName, apiKey)
    }

    override suspend fun isAddressExist(
        cityName: String,
        streetName: String,
        houseNumber: String,
        apiKey: String
    ): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getAddresses(cityName: String, streetName: String, apiKey: String): List<AddressModelDto> {
        return api.getAddresses(cityName, streetName, apiKey)
    }
}