package com.example.glazovnetadminapp.data.mappers

import com.example.glazovnetadminapp.domain.models.clients.ClientModel
import com.example.glazovnetadminapp.entity.clientsDto.ClientModelDto
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

fun ClientModelDto.toClientModel(): ClientModel {
    val debitDate = OffsetDateTime.parse(
        this.debitDate,
        DateTimeFormatter.ISO_DATE_TIME
    )
    return ClientModel(
        id = this.id,
        accountNumber = this.accountNumber,
        login = this.login,
        password = this.password,
        lastName = this.lastName,
        firstName = this.firstName,
        middleName = this.middleName,
        address = this.address,
        balance = this.balance,
        debitDate = debitDate,
        isAccountActive = this.isAccountActive,
        connectedServices = this.connectedServices
    )
}

fun ClientModel.toClientModelDto(): ClientModelDto {
    val debitDate = this.debitDate.format(DateTimeFormatter.ISO_DATE_TIME)
    return ClientModelDto(
        id = this.id,
        accountNumber = this.accountNumber,
        login = this.login,
        password = this.password,
        lastName = this.lastName,
        firstName = this.firstName,
        middleName = this.middleName,
        address = this.address,
        balance = this.balance,
        debitDate = debitDate,
        isAccountActive = this.isAccountActive,
        connectedServices = this.connectedServices
    )
}