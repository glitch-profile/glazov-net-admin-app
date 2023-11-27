package com.example.glazovnetadminapp.entity.clientsDto

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class ClientAddressModelDto(
    @field:Json(name="cityName")
    val city: String,
    @field:Json(name="streetName")
    val street: String,
    @field:Json(name="houseNumber")
    val houseNumber: String,
    @field:Json(name="roomNumber")
    val roomNumber: String
)
