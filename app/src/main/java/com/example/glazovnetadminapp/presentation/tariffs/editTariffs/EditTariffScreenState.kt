package com.example.glazovnetadminapp.presentation.tariffs.editTariffs

import com.example.glazovnetadminapp.domain.models.tariffs.TariffModel

data class EditTariffScreenState(
    val tariff: TariffModel? = null,
    val isLoading: Boolean = false,
    val message: String? = null
)
