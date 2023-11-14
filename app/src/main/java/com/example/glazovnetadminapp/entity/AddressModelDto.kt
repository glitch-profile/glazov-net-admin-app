package com.example.glazovnetadminapp.entity

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class AddressModelDto(
    @field:Json(name = "city")
    val city: String,
    @field:Json(name = "street")
    val street: String,
    @field:Json(name = "houseNumbers")
    val houseNumbers: List<String>
)
