package com.example.glazovnetadminapp.presentation.clients

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.glazovnetadminapp.domain.models.clients.ClientModel
import com.example.glazovnetadminapp.domain.useCases.ClientsUseCase
import com.example.glazovnetadminapp.domain.util.Resource
import com.example.glazovnetadminapp.presentation.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientsViewModel @Inject constructor(
    private val clientsUseCase: ClientsUseCase
): ViewModel() {

    private val _clientsScreenState = MutableStateFlow(ScreenState<ClientModel>())
    val clientsScreenState = _clientsScreenState.asStateFlow()

    private val _addClientScreenState = MutableStateFlow(ScreenState<ClientModel>())
    val addClientScreenState = _addClientScreenState.asStateFlow()

    init {
        getAllClients()
    }

    fun getAllClients() {
        viewModelScope.launch {
            _clientsScreenState.update {
                it.copy(
                    isLoading = true,
                    message = null
                )
            }
            val result = clientsUseCase.getAllClients()
            when (result) {
                is Resource.Success -> {
                    _clientsScreenState.update {
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
                    Log.i("TAG", "getAllClients: request successful")
                }
                is Resource.Error -> {
                    _clientsScreenState.update {
                        it.copy(
                            isLoading = false,
                            message = result.message
                        )
                    }
                }
            }
        }
    }

}