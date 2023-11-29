package com.example.glazovnetadminapp.entity.clientsDto

import androidx.annotation.Keep
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
@Keep
data class ClientModelDto(
    @Transient
    val id: String = "",
    val accountNumber: String,
    val login: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val middleName: String? = null,
    val address: ClientAddressModelDto,
    val balance: Double,
    //val accountCreationDate: String,
    val debitDate: String,
    val isAccountActive: Boolean,
    val connectedServices: List<String>
)
