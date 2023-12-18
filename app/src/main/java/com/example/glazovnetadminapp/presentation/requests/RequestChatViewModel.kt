package com.example.glazovnetadminapp.presentation.requests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.glazovnetadminapp.domain.models.support.MessageModel
import com.example.glazovnetadminapp.domain.models.support.SupportRequestModel
import com.example.glazovnetadminapp.domain.useCases.RequestChatUseCase
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

@HiltViewModel
class RequestChatViewModel @Inject constructor(
    private val requestChatUseCase: RequestChatUseCase
): ViewModel() {

    private val _requestState = MutableStateFlow(ScreenState<SupportRequestModel>())
    val requestState = _requestState.asStateFlow()
    private val _chatState = MutableStateFlow(ScreenState<MessageModel>())
    val chatState = _chatState.asStateFlow()

    fun getRequestDetails(requestId: String) {
        viewModelScope.launch {
            _requestState.update {
                it.copy(
                    isLoading = true,
                    message = null
                )
            }
            _chatState.update {
                it.copy(
                    isLoading = true,
                    message = null
                )
            }
            when (val result = requestChatUseCase.getRequestById(requestId)) {
                is Resource.Success -> {
                    if (result.data != null) {
                        _requestState.update {
                            it.copy(
                                data = listOf(result.data),
                                isLoading = false
                            )
                        }
                        _chatState.update {
                            when (val chatResult = requestChatUseCase.getMessagesForRequest(requestId)) {
                                is Resource.Success -> it.copy(
                                    isLoading = false,
                                    data = chatResult.data!!
                                )
                                is Resource.Error -> it.copy(
                                    isLoading = false,
                                    message = chatResult.message
                                )
                            }
                        }
                    } else {
                        _requestState.update {
                            it.copy(
                                isLoading = false,
                                message = "request not found"
                            )
                        }
                        _chatState.update {
                            it.copy(isLoading = false)
                        }
                    }
                }
                is Resource.Error -> {
                    _requestState.update {
                        it.copy(
                            isLoading = false,
                            message = result.message
                        )
                    }
                    _chatState.update {
                        it.copy(isLoading = false)
                    }
                }
            }
        }
    }

    fun connectToChatSocket(requestId: String) {
        getRequestDetails(requestId)
        viewModelScope.launch {
            when (val connectionResult = requestChatUseCase.initChatSocket(requestId)) {
                is Resource.Success -> {
                    requestChatUseCase.observeMessages()
                        .onEach { message ->
                            val newMessagesList = chatState.value.data.toMutableList().apply {
                                add(0, message)
                            }
                            _chatState.update {
                                it.copy(data = newMessagesList)
                            }
                        }.launchIn(viewModelScope)
                }
                is Resource.Error -> {
                    _chatState.update {
                        it.copy(message = connectionResult.message ?: "couldn't connect to chat")
                    }
                }
            }
        }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch {
            if (message.isNotBlank()) {
                val result = requestChatUseCase.sendMessage(message)
                if (result is Resource.Error) {
                    _chatState.update {
                        it.copy(message = result.message ?: "server error")
                    }
                }
            }
        }
    }

    fun disconnect() {
        viewModelScope.launch {
            requestChatUseCase.disconnect()
        }
    }

    override fun onCleared() {
        super.onCleared()
        disconnect()
    }

}