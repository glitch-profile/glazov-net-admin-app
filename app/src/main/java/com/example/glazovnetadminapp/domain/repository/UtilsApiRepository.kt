package com.example.glazovnetadminapp.domain.repository

import java.io.File

interface UtilsApiRepository {

    suspend fun uploadFile(pathToFile: String, apiKey: String): String

}