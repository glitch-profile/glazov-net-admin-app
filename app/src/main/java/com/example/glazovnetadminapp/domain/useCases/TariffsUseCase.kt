package com.example.glazovnetadminapp.domain.useCases

import com.example.glazovnetadminapp.data.mappers.ToTariffModelDto
import com.example.glazovnetadminapp.domain.models.tariffs.TariffModel
import com.example.glazovnetadminapp.domain.repository.LocalSettingsRepository
import com.example.glazovnetadminapp.domain.repository.TariffsApiRepository
import com.example.glazovnetadminapp.domain.util.Resource
import javax.inject.Inject

private const val API_KEY = "test_api_key_123"

class TariffsUseCase @Inject constructor(
    private val tariffsApiRepository: TariffsApiRepository,
    private val localSettingsRepository: LocalSettingsRepository
) {
    suspend fun getTariffs(): Resource<List<TariffModel>> {
        val token = localSettingsRepository.getLoginToken() ?: ""
        return tariffsApiRepository.getAllTariffs(token)
    }

    suspend fun updateTariff(
        tariff: TariffModel
    ): Resource<Boolean> {
        val token = localSettingsRepository.getLoginToken() ?: ""
        return tariffsApiRepository.updateTariff(
            token = token,
            tariff = tariff.ToTariffModelDto()
        )
    }

    suspend fun addTariff(
        tariff: TariffModel
    ): Resource<TariffModel?> {
        val token = localSettingsRepository.getLoginToken() ?: ""
        return tariffsApiRepository.addTariff(
            token = token,
            tariff = tariff.ToTariffModelDto()
        )
    }

    suspend fun deleteTariff(
        tariffId: String
    ): Resource<Boolean> {
        val token = localSettingsRepository.getLoginToken() ?: ""
        return tariffsApiRepository.deleteTariff(
            token = token,
            tariffId = tariffId
        )
    }
}