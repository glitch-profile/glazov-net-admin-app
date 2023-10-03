package com.example.glazovnetadminapp.data.remote

import com.example.glazovnetadminapp.entity.tariffsDto.TariffModelDto
import com.example.glazovnetadminapp.entity.tariffsDto.TariffsResponceDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

private const val PATH = "api/tariffs"

interface GlazovNetTariffsApi {

    @GET("$PATH/get")
    suspend fun getAllTariffs(
        @Query("tariffStatus") status: Boolean? = null
    ): TariffsResponceDto

    @POST("$PATH/add")
    suspend fun addTariff(
        @Body newTariff: TariffModelDto,
        @Query("api_key") apiKey: String
    ): TariffsResponceDto

    @PUT("$PATH/edit")
    suspend fun editTariff(
        @Body tariff: TariffModelDto,
        @Query("api_key") apiKey: String
    ): TariffsResponceDto

    @DELETE("$PATH/delete")
    suspend fun deleteTariffById(
        @Query("tariff_id") tariffId: String,
        @Query("api_key") apiKey: String
    ): TariffsResponceDto

}