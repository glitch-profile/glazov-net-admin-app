package com.example.glazovnetadminapp.data.repository

import com.example.glazovnetadminapp.data.mappers.toTariffModel
import com.example.glazovnetadminapp.data.remote.GlazovNetTariffsApi
import com.example.glazovnetadminapp.domain.models.tariffs.TariffModel
import com.example.glazovnetadminapp.domain.repository.TariffsApiRepository
import com.example.glazovnetadminapp.domain.util.Resource
import com.example.glazovnetadminapp.entity.tariffsDto.TariffModelDto
import javax.inject.Inject

class TariffsApiRepositoryImpl @Inject constructor(
    private val tariffsApi: GlazovNetTariffsApi
): TariffsApiRepository {
    override suspend fun getAllTariffs(): Resource<List<TariffModel?>> {
        return try {
            val result = tariffsApi.getAllTariffs()
            if (result.status) {
                Resource.Success(
                    data = result.data.map{ it?.toTariffModel() },
                    message = result.message
                )
            } else {
                Resource.Error(
                    message = result.message
                )
            }
        } catch (e: Exception) {
            Resource.Error(
                message = e.message.toString()
            )
        }
    }

    override suspend fun addTariff(apiKey: String, tariff: TariffModelDto): Resource<Boolean> {
        return try {
            val result = tariffsApi.addTariff(
                apiKey = apiKey,
                newTariff = tariff
            )
            if (result.status) {
                Resource.Success(
                    data = true,
                    message = result.message
                )
            } else {
                Resource.Error(
                    message = result.message
                )
            }
        } catch (e: Exception) {
            Resource.Error(
                message = e.message.toString()
            )
        }
    }

    override suspend fun deleteTariff(apiKey: String, tariffId: String): Resource<Boolean> {
        return try {
            val result = tariffsApi.deleteTariffById(
                apiKey = apiKey,
                tariffId = tariffId
            )
            if (result.status) {
                Resource.Success(
                    data = true,
                    message = result.message
                )
            } else {
                Resource.Error(
                    message = result.message
                )
            }
        } catch (e: Exception) {
            Resource.Error(
                message = e.message.toString()
            )
        }
    }

    override suspend fun updateTariff(apiKey: String, tariff: TariffModelDto): Resource<Boolean> {
        return try {
            val result = tariffsApi.editTariff(
                apiKey = apiKey,
                tariff = tariff
            )
            if (result.status) {
                Resource.Success(
                    data = true,
                    message = result.message
                )
            } else {
                Resource.Error(
                    message = result.message
                )
            }
        } catch (e: Exception) {
            Resource.Error(
                message = e.message.toString()
            )
        }
    }
}