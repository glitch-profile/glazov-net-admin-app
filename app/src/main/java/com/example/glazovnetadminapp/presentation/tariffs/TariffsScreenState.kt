package com.example.glazovnetadminapp.presentation.tariffs

import com.example.glazovnetadminapp.domain.models.tariffs.TariffModel

data class TariffsScreenState(
    val tariffsData: MutableList<TariffModel?> = mutableListOf(null),
    val isLoading: Boolean = false,
    val message: String? = null
)
