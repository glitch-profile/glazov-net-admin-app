package com.example.glazovnetadminapp.domain.repository

import com.example.glazovnetadminapp.domain.util.Resource
import com.example.glazovnetadminapp.entity.AuthDataDto
import java.io.File

interface UtilsApiRepository {

    suspend fun login(authData: AuthDataDto): Resource<String>

    suspend fun uploadImage(file: File, token: String): Resource<List<String>>

}