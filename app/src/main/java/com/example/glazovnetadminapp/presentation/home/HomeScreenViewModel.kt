package com.example.glazovnetadminapp.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.glazovnetadminapp.data.repository.LocalSettingsRepositoryImpl
import com.example.glazovnetadminapp.domain.useCases.UtilsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val localSettingsRepositoryImpl: LocalSettingsRepositoryImpl,
    private val utilsUseCase: UtilsUseCase
): ViewModel() {

    var isApiKeyEmpty: Boolean = true
        private set

    init {
        checkIfApiKeyEmpty()
    }

    fun checkIfApiKeyEmpty() {
        isApiKeyEmpty = localSettingsRepositoryImpl.getSavedApiKey().isBlank()
    }

    //TODO:Make a picture selection window, get selected image path, upload file with that path
    fun testUploadFile() {
        viewModelScope.launch {
            val result = utilsUseCase.uploadFiles("")
            Log.i("TAG", "testUploadFile: $result")
        }
    }

}