package com.example.glazovnetadminapp.entity.tariffsDto

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class TariffModelDto(
    @field:Json(name = "id")
    val id: String = "",
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "description")
    val description: String? = null,
    @field:Json(name = "categoryCode")
    val categoryCode: Int,
    @field:Json(name = "maxSpeed")
    val maxSpeed: Int,
    @field:Json(name = "costPerMonth")
    val costPerMonth: Int,
    @field:Json(name = "isActive")
    val isActive: Boolean
)
