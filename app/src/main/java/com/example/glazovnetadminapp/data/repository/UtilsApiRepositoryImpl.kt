package com.example.glazovnetadminapp.data.repository

import android.content.Context
import com.example.glazovnetadminapp.domain.repository.UtilsApiRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.header
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import java.io.File
import javax.inject.Inject
import javax.inject.Named

private const val PATH = "/api/utils"

class UtilsApiRepositoryImpl @Inject constructor(
    @Named("RestClient") private val client: HttpClient,
    @ApplicationContext private val context: Context,

): UtilsApiRepository {

    override suspend fun uploadFile(pathToFile: String, apiKey: String): String {
        return try {
            with(context) {
                val file = File(pathToFile)
//                val fileTemp = File(cacheDir, "myImage.png")
//                fileTemp.createNewFile()
//                fileTemp.outputStream().use {
//                    assets.open("Avatar_V2.0.png").copyTo(it)
//                }
                val response: String = client.submitFormWithBinaryData(
                    url = "$PATH/upload-file",
                    formData = formData {
                        append("image", file.readBytes(), Headers.build {
                            append(HttpHeaders.ContentType, "image/png")
                            append(HttpHeaders.ContentDisposition, "filename=${file.name}")
                        })
                    }
                ) {
                    header("api_key", apiKey)
                }.body()
                return response
            }
        } catch (e: Exception) {
            e.printStackTrace()
            e.message ?: ""
        }
    }

}