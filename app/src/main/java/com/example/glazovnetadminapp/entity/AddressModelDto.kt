package com.example.glazovnetadminapp.entity

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class AddressModelDto(
    val city: String,
    val street: String,
    val houseNumbers: List<String>
)
