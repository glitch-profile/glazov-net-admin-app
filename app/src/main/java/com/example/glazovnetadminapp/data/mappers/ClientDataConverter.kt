package com.example.glazovnetadminapp.data.mappers

import com.example.glazovnetadminapp.domain.models.clients.ClientModel
import com.example.glazovnetadminapp.entity.clientsDto.ClientModelDto
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.Period
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun ClientModelDto.toClientModel(): ClientModel {
    val debitDate = LocalDate.parse(
        this.debitDate,
        DateTimeFormatter.ISO_DATE
    )
    val nextDebitDateOffset = Period.between(debitDate, LocalDate.now(ZoneId.systemDefault()))
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
        nextDebitDateOffset = nextDebitDateOffset.days,
        isAccountActive = this.isAccountActive,
        connectedServices = this.connectedServices
    )
}

fun ClientModel.toClientModelDto(): ClientModelDto {
    val debitDate = this.debitDate?.format(DateTimeFormatter.ISO_DATE) ?: ""
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