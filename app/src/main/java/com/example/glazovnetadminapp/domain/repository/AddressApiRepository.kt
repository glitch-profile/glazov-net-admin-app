package com.example.glazovnetadminapp.domain.repository

import com.example.glazovnetadminapp.domain.models.announcements.AddressFilterElement
import com.example.glazovnetadminapp.domain.util.Resource

interface AddressApiRepository {

    suspend fun getStreetsWithName(
        cityName: String,
        streetName: String,
        token: String
    ): Resource<List<String>>

    suspend fun getCitiesWithName(
        cityName: String,
        token: String
    ): Resource<List<String>>

    suspend fun isAddressExist(
        cityName: String,
        streetName: String,
        houseNumber: String,
        token: String
    ): Resource<Boolean>

    suspend fun getAddresses(
        cityName: String,
        streetName: String,
        token: String
    ): Resource<List<AddressFilterElement>>

}