package com.example.glazovnetadminapp.domain.useCases

import com.example.glazovnetadminapp.data.repository.AddressApiRepositoryImpl
import com.example.glazovnetadminapp.data.repository.LocalSettingsRepositoryImpl
import com.example.glazovnetadminapp.domain.models.announcements.AddressFilterElement
import com.example.glazovnetadminapp.domain.util.Resource

class AddressesUseCase(
    private val addressApiRepository: AddressApiRepositoryImpl,
    private val localSettingsRepository: LocalSettingsRepositoryImpl
) {

    suspend fun getCitiesList(
        cityName: String
    ): Resource<List<String>> {
        val apiKey = localSettingsRepository.getSavedApiKey()
        return addressApiRepository.getCitiesWithName(
            cityName = cityName,
            apiKey = apiKey
        )
    }

    suspend fun getStreetsList(
        cityName: String,
        streetName: String
    ): Resource<List<String>> {
        return if (cityName.isNotBlank() && streetName.isNotBlank()) {
            val apiKey = localSettingsRepository.getSavedApiKey()
            addressApiRepository.getStreetsWithName(
                cityName = cityName,
                streetName = streetName,
                apiKey = apiKey
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
        val apiKey = localSettingsRepository.getSavedApiKey()
        return addressApiRepository.getAddresses(cityName, streetName, apiKey)
    }

}