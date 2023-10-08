package com.example.glazovnetadminapp.domain.models.tariffs

import com.example.glazovnetadminapp.R

sealed class TariffType(
    val description: String,
    val stringResourceId: Int
) {
    data object Archive: TariffType(
        description = "Archive",
        stringResourceId = R.string.tariff_type_archive
    )
    data object Limited: TariffType(
        description = "Limited",
        stringResourceId = R.string.tariff_type_limited
    )
    data object Unlimited: TariffType(
        description = "Unlimited",
        stringResourceId = R.string.tariff_type_unlimited
    )

    companion object {
        fun fromTariffTypeCode(code: Int): TariffType {
            return when (code) {
                -1 -> Archive
                0 -> Limited
                1 -> Unlimited
                else -> Unlimited
            }
        }
        fun TariffType.toTariffTypeCode(): Int {
            return when (this) {
                Archive -> -1
                Limited -> 0
                Unlimited -> 1
            }
        }
    }
}
