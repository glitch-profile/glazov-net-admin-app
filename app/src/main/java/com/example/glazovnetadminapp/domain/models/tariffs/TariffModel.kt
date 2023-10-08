package com.example.glazovnetadminapp.domain.models.tariffs

import androidx.annotation.Keep

@Keep
data class TariffModel(
    val id: String,
    val name: String,
    val description: String? = null,
    val category: TariffType,
    val maxSpeed: Int,
    val costPerMonth: Int
)
