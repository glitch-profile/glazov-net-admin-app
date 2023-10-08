package com.example.glazovnetadminapp.data.mappers

import com.example.glazovnetadminapp.domain.models.tariffs.TariffModel
import com.example.glazovnetadminapp.domain.models.tariffs.TariffType
import com.example.glazovnetadminapp.domain.models.tariffs.TariffType.Companion.toTariffTypeCode
import com.example.glazovnetadminapp.entity.tariffsDto.TariffModelDto

fun TariffModelDto.toTariffModel(): TariffModel {
    return TariffModel(
        id = id,
        name = name,
        description = description,
        category = TariffType.fromTariffTypeCode(this.categoryCode),
        maxSpeed = maxSpeed,
        costPerMonth = costPerMonth
    )
}

fun TariffModel.ToTariffModelDto(): TariffModelDto {
    return TariffModelDto(
        id = id,
        name = name,
        categoryCode = this.category.toTariffTypeCode(),
        description = description,
        maxSpeed = maxSpeed,
        costPerMonth = costPerMonth
    )
}