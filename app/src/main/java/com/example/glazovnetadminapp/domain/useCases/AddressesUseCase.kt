package com.example.glazovnetadminapp.domain.useCases

import com.example.glazovnetadminapp.data.repository.AddressApiRepositoryImpl
import com.example.glazovnetadminapp.data.repository.LocalSettingsRepositoryImpl
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