package com.example.glazovnetadminapp.presentation.tariffs.tariffsList

import com.example.glazovnetadminapp.domain.models.tariffs.TariffModel

data class TariffsScreenState(
    val tariffsData: MutableList<TariffModel> = mutableListOf(),
    val isLoading: Boolean = false,
    val message: String? = null
)
