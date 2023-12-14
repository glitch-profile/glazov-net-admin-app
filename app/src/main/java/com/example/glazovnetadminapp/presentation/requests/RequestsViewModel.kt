package com.example.glazovnetadminapp.presentation.requests

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.glazovnetadminapp.domain.models.support.SupportRequestModel
import com.example.glazovnetadminapp.domain.useCases.RequestsUseCase
import com.example.glazovnetadminapp.domain.util.Resource
import com.example.glazovnetadminapp.presentation.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "WebSockets"

@HiltViewModel
class RequestsViewModel @Inject constructor(
    private val requestsUseCase: RequestsUseCase
): ViewModel() {


    private val _state = MutableStateFlow(ScreenState<SupportRequestModel>())
    val state = _state.asStateFlow()

    init {
        connectToSocket()
    }

    fun getAllRequests() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    message = null
                )
            }
            when (val result = requestsUseCase.getAllRequests()) {
                is Resource.Success -> {
                    _state.update {
                        if (result.data!!.isNotEmpty()) {
                            it.copy(
                                isLoading = false,
                                data = result.data
                            )
                        } else {
                            it.copy(
                                isLoading = false,
                                message = result.message
                            )
                        }
                    }
                }
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            message = result.message
                        )
                    }
                }
            }
        }
    }

    fun connectToSocket() {
        getAllRequests()
        viewModelScope.launch {
            val result = requestsUseCase.initRequestsSocket()
            when (result) {
                is Resource.Success -> {
                    requestsUseCase.observeMessages()
                        .onEach {request ->
                            val newRequestsList = state.value.data.toMutableList().apply {
                                add(0, request)
                            }
                            _state.update {
                                it.copy(data = newRequestsList)
                            }
                        }.launchIn(viewModelScope)
                }
                is Resource.Error -> {
                    _state.update {
                        it.copy(message = result.message).also {
                            Log.e(TAG, "connectToSocket: cant init session", )
                        }
                    }
                }
            }
        }
    }

    fun disconnect() {
        viewModelScope.launch {
            requestsUseCase.disconnect()
        }
    }

    override fun onCleared() {
        disconnect()
        super.onCleared()
    }

}