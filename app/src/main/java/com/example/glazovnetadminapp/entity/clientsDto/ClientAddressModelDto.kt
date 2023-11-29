package com.example.glazovnetadminapp.entity.clientsDto

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class ClientAddressModelDto(
    val cityName: String,
    val streetName: String,
    val houseNumber: String,
    val roomNumber: String
)
