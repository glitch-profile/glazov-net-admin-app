package com.example.glazovnetadminapp.domain.models.clients

import com.example.glazovnetadminapp.entity.clientsDto.ClientAddressModelDto
import java.time.OffsetDateTime

data class ClientModel(
    val id: String,
    val accountNumber: String,
    val login: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val middleName: String? = null,
    val address: ClientAddressModelDto,
    val balance: Double,
    val debitDate: OffsetDateTime,
    val isAccountActive: Boolean,
    val connectedServices: List<String>,
)