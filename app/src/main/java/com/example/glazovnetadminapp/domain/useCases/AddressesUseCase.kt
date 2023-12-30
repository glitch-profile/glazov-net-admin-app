package com.example.glazovnetadminapp.domain.useCases

import com.example.glazovnetadminapp.domain.models.announcements.AddressFilterElement
import com.example.glazovnetadminapp.domain.repository.AddressApiRepository
import com.example.glazovnetadminapp.domain.repository.LocalSettingsRepository
import com.example.glazovnetadminapp.domain.util.Resource

class AddressesUseCase(
    private val addressApiRepository: AddressApiRepository,
    private val localSettingsRepository: LocalSettingsRepository
) {

    suspend fun getCitiesList(
        cityName: String
    ): Resource<List<String>> {
        val token = localSettingsRepository.getLoginToken() ?: ""
        return addressApiRepository.getCitiesWithName(
            cityName = cityName,
            token = token
        )
    }

    suspend fun getStreetsList(
        cityName: String,
        streetName: String
    ): Resource<List<String>> {
        return if (cityName.isNotBlank() && streetName.isNotBlank()) {
            val token = localSettingsRepository.getLoginToken() ?: ""
            addressApiRepository.getStreetsWithName(
                cityName = cityName,
                streetName = streetName,
                token = token
            )
        } else {
            Resource.Success(
                data = emptyList(),
                message = "not enough arguments"
            )
        }
    }

    suspend fun getAddresses(
        cityName: String,
        streetName: String
    ): Resource<List<AddressFilterElement>> {
        val token = localSettingsRepository.getLoginToken() ?: ""
        return addressApiRepository.getAddresses(cityName, streetName, token)
    }

}