package com.example.glazovnetadminapp.domain.repository

import com.example.glazovnetadminapp.entity.AddressModelDto

interface AddressApiRepository {

    suspend fun getStreetsWithName(
        cityName: String,
        streetName: String,
        apiKey: String
    ): List<String>

    suspend fun getCitiesWithName(
        cityName: String,
        apiKey: String
    ): List<String>

    suspend fun isAddressExist(
        cityName: String,
        streetName: String,
        houseNumber: String,
        apiKey: String
    ): Boolean

    suspend fun getAddresses(
        cityName: String,
        streetName: String,
        apiKey: String
    ): List<AddressModelDto>

}