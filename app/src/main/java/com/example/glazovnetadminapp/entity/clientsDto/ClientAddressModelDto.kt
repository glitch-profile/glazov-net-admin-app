package com.example.glazovnetadminapp.entity.clientsDto

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class ClientAddressModelDto(
    val city: String,
    val street: String,
    val houseNumber: String,
    val roomNumber: String
)
