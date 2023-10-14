package com.example.glazovnetadminapp.presentation.tariffs.tariffsList

import com.example.glazovnetadminapp.domain.models.tariffs.TariffModel

data class TariffsScreenState(
    val tariffsData: List<TariffModel> = listOf(),
    val isLoading: Boolean = false,
    val message: String? = null
)
