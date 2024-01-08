package com.example.glazovnetadminapp.domain.repository

import com.example.glazovnetadminapp.domain.util.Resource
import com.example.glazovnetadminapp.entity.authDto.AuthDataDto
import com.example.glazovnetadminapp.entity.authDto.AuthResponse
import java.io.File

interface UtilsApiRepository {

    suspend fun login(authData: AuthDataDto): Resource<AuthResponse>

    suspend fun uploadImage(file: File, token: String): Resource<List<String>>

}