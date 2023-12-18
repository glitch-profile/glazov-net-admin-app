package com.example.glazovnetadminapp.domain.repository

import android.content.SharedPreferences

interface LocalSettingsRepository {

    val preferences: SharedPreferences

    fun getSavedApiKey(): String
    fun setSavedApiKey(apiKey: String?)

    fun getMemberId(): String
    fun setMemberId(memberId: String)
}