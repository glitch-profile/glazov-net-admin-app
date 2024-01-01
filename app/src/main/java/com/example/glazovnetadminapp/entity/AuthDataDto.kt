package com.example.glazovnetadminapp.entity

import kotlinx.serialization.Serializable

@Serializable
data class AuthDataDto(
    val login: String,
    val password: String,
    val asAdmin: Boolean
)
