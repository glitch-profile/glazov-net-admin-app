package com.example.glazovnetadminapp.domain.useCases

import com.example.glazovnetadminapp.data.repository.TariffsApiRepositoryImpl
import com.example.glazovnetadminapp.domain.models.tariffs.TariffModel
import com.example.glazovnetadminapp.domain.util.Resource
import javax.inject.Inject

private const val API_KEY = "test_api_key_123"

class TariffsUseCase @Inject constructor(
    private val tariffsApiRepository: TariffsApiRepositoryImpl
) {
    suspend fun getTariffs(
        status: Boolean? = null
    ): Resource<List<TariffModel?>> {
        return tariffsApiRepository.getAllTariffs(status)
    }
}