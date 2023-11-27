package com.example.glazovnetadminapp.entity.clientsDto

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class ClientModelDto(
    @Transient
    @field:Json(name="id")
    val id: String,
    @field:Json(name="accountNumber")
    val accountNumber: String,
    @field:Json(name="login")
    val login: String,
    @field:Json(name="password")
    val password: String,
    @field:Json(name="firstName")
    val firstName: String,
    @field:Json(name="lastName")
    val lastName: String,
    @field:Json(name="middleName")
    val middleName: String? = null,
    @field:Json(name="address")
    val address: ClientAddressModelDto,
    @field:Json(name="balance")
    val balance: Double,
    @field:Json(name="debitDate")
    val debitDate: String,
    @field:Json(name="isAccountActive")
    val isAccountActive: Boolean,
    @field:Json(name="connectedServices")
    val connectedServices: List<String>
)
