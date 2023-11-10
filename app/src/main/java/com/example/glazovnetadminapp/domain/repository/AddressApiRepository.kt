package com.example.glazovnetadminapp.domain.repository

interface AddressApiRepository {

    suspend fun getStreetsWithName(
        cityName: String,
        streetName: String
    ): List<String>

    suspend fun checkIfStreetExists(
        cityName: String,
        streetName: String
    ): Boolean

    suspend fun getCitiesWithName(
        cityName: String
    ): List<String>

    suspend fun checkIfCityExists(
        cityName: String
    ): Boolean

}