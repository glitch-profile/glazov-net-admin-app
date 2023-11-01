package com.example.glazovnetadminapp.entity.filtersDto

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class FilterModelDto(
    @field:Json(name = "id")
    val id: String = "",
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "addressFilters")
    val addressFilters: List<List<String>>
)
