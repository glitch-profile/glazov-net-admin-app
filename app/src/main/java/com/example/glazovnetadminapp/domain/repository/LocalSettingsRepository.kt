package com.example.glazovnetadminapp.domain.repository

import android.content.SharedPreferences

interface LocalSettingsRepository {

    val preferences: SharedPreferences

    fun getSavedApiKey(): String?
    fun setSavedApiKey(apiKey: String?)

    fun getLoginToken(): String?
    fun setLoginToken(loginToken: String?, isNeedToSave: Boolean)

    fun getAssociatedUserId(): String?
    fun setAssociatedUserId(userId: String?, isNeedToSave: Boolean)
}