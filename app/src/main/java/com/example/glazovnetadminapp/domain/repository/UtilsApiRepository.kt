package com.example.glazovnetadminapp.domain.repository

import com.example.glazovnetadminapp.domain.util.Resource
import java.io.File

interface UtilsApiRepository {

    suspend fun uploadImage(file: File, apiKey: String): Resource<List<String>>

}