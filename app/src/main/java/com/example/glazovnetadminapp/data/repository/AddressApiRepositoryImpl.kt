package com.example.glazovnetadminapp.data.repository

import com.example.glazovnetadminapp.data.remote.GlazovNetApi
import com.example.glazovnetadminapp.domain.repository.AddressApiRepository
import javax.inject.Inject

class AddressApiRepositoryImpl @Inject constructor(
    private val api: GlazovNetApi
): AddressApiRepository {
    override suspend fun getStreetsWithName(cityName: String, streetName: String): List<String> {
        return api.getStreetsList(cityName, streetName)
    }

    override suspend fun checkIfStreetExists(cityName: String, streetName: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getCitiesWithName(cityName: String): List<String> {
        return api.getCitiesList(cityName)
    }

    override suspend fun checkIfCityExists(cityName: String): Boolean {
        TODO("Not yet implemented")
    }
}