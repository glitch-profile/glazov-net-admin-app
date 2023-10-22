package com.example.glazovnetadminapp.data.repository

import android.content.Context
import android.util.Log
import com.example.glazovnetadminapp.domain.repository.LocalSettingsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val PREFERENCE_NAME = "GlazovNetPreferences"
private const val API_KEY_NAME = "GlazovNetApiKey"

class LocalSettingsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
): LocalSettingsRepository {

    override fun getSavedApiKey(): String {
        val preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        return preferences.getString(API_KEY_NAME, "").toString().also {
            Log.i("TAG", "ApiKeyFromPreffs - $it ")
        }
    }

    override fun setSavedApiKey(apiKey: String?) {
        val preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        val apiKeyToSave = apiKey?.take(64) ?: ""
        preferences.edit().putString(API_KEY_NAME, apiKeyToSave).apply()
    }
}