package com.example.glazovnetadminapp.domain.models.tariffs

import com.example.glazovnetadminapp.R

sealed class TariffType(
    val description: String,
    val stringResourceId: Int
) {
    object Limited: TariffType(
        description = "Limited",
        stringResourceId = R.string.tariff_type_limited
    )
    object Unlimited: TariffType(
        description = "Unlimited",
        stringResourceId = R.string.tariff_type_unlimited
    )

    companion object {
        fun fromTariffTypeCode(code: Int): TariffType {
            return when (code) {
                0 -> Limited
                1 -> Unlimited
                else -> Unlimited
            }
        }
        fun TariffType.toTariffTypeCode(): Int {
            return when (this) {
                Limited -> 0
                Unlimited -> 1
            }
        }
    }
}
