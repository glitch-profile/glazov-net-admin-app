package com.example.glazovnetadminapp.presentation.clients

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.glazovnetadminapp.domain.models.clients.ClientModel
import com.example.glazovnetadminapp.domain.useCases.AddressesUseCase
import com.example.glazovnetadminapp.domain.useCases.ClientsUseCase
import com.example.glazovnetadminapp.domain.util.Resource
import com.example.glazovnetadminapp.presentation.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientsViewModel @Inject constructor(
    private val clientsUseCase: ClientsUseCase,
    private val addressesUseCase: AddressesUseCase
): ViewModel() {

    private val _clientsScreenState = MutableStateFlow(ScreenState<ClientModel>())
    val clientsScreenState = _clientsScreenState.asStateFlow()

    private val _addClientScreenState = MutableStateFlow(ScreenState<ClientModel>())
    val addClientScreenState = _addClientScreenState.asStateFlow()

    private val _citiesSearchText = MutableStateFlow("")
    private val citiesSearchJob = _citiesSearchText
        .debounce(300)
        .distinctUntilChanged()
        .flowOn(Dispatchers.IO)
        .onEach {
            when (val result = addressesUseCase.getCitiesList(it)) {
                is Resource.Success -> {
                    _citiesList.update {
                        result.data!!
                    }
                }
                is Resource.Error -> {
                    _citiesList.update {
                        emptyList()
                    }
                }
            }
        }
        .launchIn(viewModelScope)
    private val _citiesList = MutableStateFlow(emptyList<String>())
    val citiesList = _citiesList.asStateFlow()

    private val _streetsSearchText = MutableStateFlow("")
    private val streetsSearchJob = _streetsSearchText
        .debounce(300)
        .distinctUntilChanged()
        .flowOn(Dispatchers.IO)
        .onEach {
            val result = addressesUseCase.getStreetsList(_citiesSearchText.value, it)
            when (result) {
                is Resource.Success -> {
                    _streetsList.update {
                        result.data!!
                    }
                }
                is Resource.Error -> {
                    _streetsList.update {
                        emptyList()
                    }
                }
            }
        }
        .launchIn(viewModelScope)
    private val _streetsList = MutableStateFlow(emptyList<String>())
    val streetsList = _streetsList.asStateFlow()


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

    fun createClient(
        clientModel: ClientModel
    ) {
        viewModelScope.launch {

        }
    }

    fun updateCitiesSearch(
        citiesSearch: String
    ) {
        _citiesSearchText.update { citiesSearch }
    }

    fun updateStreetsSearch(
        streetsSearch: String
    ) {
        _streetsSearchText.update { streetsSearch }
    }

}