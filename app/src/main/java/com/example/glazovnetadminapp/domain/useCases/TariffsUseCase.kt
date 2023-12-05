package com.example.glazovnetadminapp.domain.useCases

import com.example.glazovnetadminapp.data.mappers.ToTariffModelDto
import com.example.glazovnetadminapp.data.repository.LocalSettingsRepositoryImpl
import com.example.glazovnetadminapp.data.repository.TariffsApiRepositoryImpl
import com.example.glazovnetadminapp.domain.models.tariffs.TariffModel
import com.example.glazovnetadminapp.domain.repository.LocalSettingsRepository
import com.example.glazovnetadminapp.domain.repository.TariffsApiRepository
import com.example.glazovnetadminapp.domain.util.Resource
import javax.inject.Inject

private const val API_KEY = "test_api_key_123"

class TariffsUseCase @Inject constructor(
    private val tariffsApiRepository: TariffsApiRepository,
    private val localSettingsRepositoryImpl: LocalSettingsRepository
) {
    suspend fun getTariffs(): Resource<List<TariffModel>> {
        return tariffsApiRepository.getAllTariffs()
    }

    suspend fun updateTariff(
        tariff: TariffModel
    ): Resource<Boolean> {
        return tariffsApiRepository.updateTariff(
            apiKey = localSettingsRepositoryImpl.getSavedApiKey(),
            tariff.ToTariffModelDto()
        )
    }

    suspend fun addTariff(
        tariff: TariffModel
    ): Resource<TariffModel?> {
        return tariffsApiRepository.addTariff(
            apiKey = localSettingsRepositoryImpl.getSavedApiKey(),
            tariff = tariff.ToTariffModelDto()
        )
    }

    suspend fun deleteTariff(
        tariffId: String
    ): Resource<Boolean> {
        return tariffsApiRepository.deleteTariff(
            apiKey = localSettingsRepositoryImpl.getSavedApiKey(),
            tariffId = tariffId
        )
    }
}