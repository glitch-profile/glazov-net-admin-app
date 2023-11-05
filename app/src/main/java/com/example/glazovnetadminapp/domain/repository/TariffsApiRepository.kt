package com.example.glazovnetadminapp.domain.repository

import com.example.glazovnetadminapp.domain.models.tariffs.TariffModel
import com.example.glazovnetadminapp.domain.util.Resource
import com.example.glazovnetadminapp.entity.tariffsDto.TariffModelDto

interface TariffsApiRepository {

    suspend fun getAllTariffs(): Resource<List<TariffModel>>

    suspend fun addTariff(
        apiKey: String,
        tariff: TariffModelDto
    ): Resource<TariffModel?>

    suspend fun deleteTariff(
        apiKey: String,
        tariffId: String
    ): Resource<Boolean>

    suspend fun updateTariff(
        apiKey: String,
        tariff: TariffModelDto
    ): Resource<Boolean>

}