package com.example.glazovnetadminapp.entity

import kotlinx.serialization.Serializable

@Serializable
data class AuthDataDto(
    val username: String,
    val password: String,
    val asAdmin: Boolean
)
