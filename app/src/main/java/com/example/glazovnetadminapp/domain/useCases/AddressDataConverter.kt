package com.example.glazovnetadminapp.domain.useCases

import com.example.glazovnetadminapp.domain.models.announcements.AddressFilterElement
import com.example.glazovnetadminapp.entity.AddressModelDto

fun AddressModelDto.toAddressFilterElement(): List<AddressFilterElement> {
    val cityName = this.city.replaceFirstChar { it.uppercaseChar() }
    val streetName = this.street.replaceFirstChar { it.uppercaseChar() }
    val addresses = this.houseNumbers.map {
        AddressFilterElement(
            city = cityName,
            street = streetName,
            houseNumber = it
        )
    }
    return addresses
}