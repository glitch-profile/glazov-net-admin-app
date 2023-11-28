package com.example.glazovnetadminapp.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.glazovnetadminapp.domain.repository.LocalSettingsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val PREFERENCE_NAME = "GlazovNetPreferences"
private const val API_KEY_NAME = "GlazovNetApiKey"

class LocalSettingsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
): LocalSettingsRepository {

    override val preferences: SharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    override fun getSavedApiKey(): String {
        return preferences.getString(API_KEY_NAME, "").toString()
    }
    override fun setSavedApiKey(apiKey: String?) {
        val apiKeyToSave = apiKey?.take(64) ?: ""
        preferences.edit().putString(API_KEY_NAME, apiKeyToSave).apply()
    }
}