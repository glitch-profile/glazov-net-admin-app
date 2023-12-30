package com.example.glazovnetadminapp.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.glazovnetadminapp.domain.repository.LocalSettingsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val PREFERENCE_NAME = "GlazovNetPreferences"
private const val API_KEY_NAME = "ApiKey"
private const val LOGIN_TOKEN_NAME = "LoginToken"
private const val USER_ID_NAME = "UserId"

class LocalSettingsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
): LocalSettingsRepository {

    override val preferences: SharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    override fun getSavedApiKey(): String? {
        return preferences.getString(API_KEY_NAME, null)
    }
    override fun setSavedApiKey(apiKey: String?) {
        val apiKeyToSave = apiKey?.take(64)
        preferences.edit().putString(API_KEY_NAME, apiKeyToSave).apply()
    }

    private var savedLoginToken: String? = null
    override fun getLoginToken(): String? {
        return if (savedLoginToken != null) savedLoginToken
        else  {
            val loginToken = preferences.getString(LOGIN_TOKEN_NAME, null)
            if (loginToken != null) savedLoginToken = loginToken
            loginToken
        }
    }
    override fun setLoginToken(loginToken: String?, isNeedToSave: Boolean) {
        savedLoginToken = loginToken
        if (isNeedToSave) preferences.edit().putString(LOGIN_TOKEN_NAME, loginToken).apply()
    }

    private var associatedUserId: String? = null
    override fun getAssociatedUserId(): String? {
        return if (associatedUserId != null) associatedUserId
        else {
            val userId = preferences.getString(USER_ID_NAME, null)
            if (userId != null) associatedUserId = userId
            userId
        }
    }
    override fun setAssociatedUserId(userId: String?, isNeedToSave: Boolean) {
        associatedUserId = userId
        if (isNeedToSave) preferences.edit().putString(USER_ID_NAME, userId).apply()
    }
}