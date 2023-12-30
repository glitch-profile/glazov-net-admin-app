package com.example.glazovnetadminapp.domain.repository

import com.example.glazovnetadminapp.domain.models.tariffs.TariffModel
import com.example.glazovnetadminapp.domain.util.Resource
import com.example.glazovnetadminapp.entity.tariffsDto.TariffModelDto

interface TariffsApiRepository {

    suspend fun getAllTariffs(
        token: String
    ): Resource<List<TariffModel>>

    suspend fun addTariff(
        tariff: TariffModelDto,
        token: String
    ): Resource<TariffModel?>

    suspend fun deleteTariff(
        tariffId: String,
        token: String
    ): Resource<Boolean>

    suspend fun updateTariff(
        tariff: TariffModelDto,
        token: String
    ): Resource<Boolean>

}