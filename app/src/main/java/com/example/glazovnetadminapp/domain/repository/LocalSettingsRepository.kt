package com.example.glazovnetadminapp.domain.repository

interface LocalSettingsRepository {

    fun getSavedApiKey(): String

    fun setSavedApiKey(apiKey: String?)

}