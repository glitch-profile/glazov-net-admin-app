package com.example.glazovnetadminapp.entity.tariffsDto

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class TariffModelDto(
    val id: String = "",
    val name: String,
    val description: String? = null,
    val categoryCode: Int,
    val maxSpeed: Int,
    val costPerMonth: Int
)
