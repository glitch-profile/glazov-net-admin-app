package com.example.glazovnetadminapp.domain.repository

import android.content.Context

interface LocalSettingsRepository {

    fun getSavedApiKey(): String

    fun setSavedApiKey(apiKey: String?)

}