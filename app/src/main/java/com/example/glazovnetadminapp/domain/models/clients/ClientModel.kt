package com.example.glazovnetadminapp.domain.models.clients

import com.example.glazovnetadminapp.entity.clientsDto.ClientAddressModelDto
import java.time.LocalDate
import java.time.ZoneId

data class ClientModel(
    val id: String,
    val accountNumber: String,
    val login: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val middleName: String,
    val address: ClientAddressModelDto,
    val balance: Double = 0.0,
    val debitDate: LocalDate? = null,
    val nextDebitDateOffset: Int? = null,
    val isAccountActive: Boolean = true,
    val connectedServices: List<String> = emptyList(),
)