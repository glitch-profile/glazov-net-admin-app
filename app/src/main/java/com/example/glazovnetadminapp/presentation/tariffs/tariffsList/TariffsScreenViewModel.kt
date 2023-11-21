package com.example.glazovnetadminapp.presentation.tariffs.tariffsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.glazovnetadminapp.domain.models.tariffs.TariffModel
import com.example.glazovnetadminapp.domain.useCases.TariffsUseCase
import com.example.glazovnetadminapp.domain.util.Resource
import com.example.glazovnetadminapp.presentation.ScreenState
import com.example.glazovnetadminapp.presentation.tariffs.editTariffs.EditTariffScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TariffsScreenViewModel @Inject constructor(
    private val tariffsUseCase: TariffsUseCase
): ViewModel() {

    private val _state = MutableStateFlow(ScreenState<TariffModel>())
    val state = _state.asStateFlow()

    private val _nameFilter = MutableStateFlow("")
    val nameFilter = _nameFilter.asStateFlow()

    val filteredTariffs = state
        .combine(nameFilter) {state, name ->
            if (name.isBlank()) {
                state.data
            } else {
                state.data.filter {
                    it.name.contains(name)
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            initialValue = listOf()
        )

    private var _editTariffState = MutableStateFlow(EditTariffScreenState())
    val editTariffState = _editTariffState.asStateFlow()

    init {
        loadTariffs()
    }

    fun setTariffToEdit(tariff: TariffModel?) {
        _editTariffState.update {
            EditTariffScreenState(
                tariff = tariff
            )
        }
    }

    fun updateNameFilter(string: String) {
        _nameFilter.update { string }
    }

    fun loadTariffs() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    message = null
                )
            }
            val result = tariffsUseCase.getTariffs()
            when (result) {
                is Resource.Success -> {
                    _state.update {
                        if (result.data !== null) {
                            it.copy(
                                data = result.data.filterNotNull(),
                                isLoading = false
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


    fun updateTariff(
        tariff: TariffModel
    ) {
        viewModelScope.launch {
            _editTariffState.update{
                it.copy(
                    isLoading = true,
                    message = "updating tariff..."
                )
            }
            val result = tariffsUseCase.updateTariff(tariff = tariff)
            if (result.data == true) {
                val tariffIndex = state.value.data.indexOfFirst {
                    it.id == tariff.id
                }
                if (tariffIndex == -1) {
                    _editTariffState.update{
                        it.copy(
                            isLoading = false,
                            message = "error while updating tariff locally"
                        )
                    }
                }
                else {
                    val newTariffsList = state.value.data.toMutableList()
                    newTariffsList[tariffIndex] = tariff
                    _state.update {
                        it.copy(
                            data = newTariffsList
                        )
                    }
                    _editTariffState.update{
                        it.copy(
                            isLoading = false,
                            message = "completed"
                        )
                    }
                }
            } else {
                _editTariffState.update{
                    it.copy(
                        isLoading = false,
                        message = result.message
                    )
                }
            }
        }
    }

    fun removeTariff(
        tariff: TariffModel
    ) {
        viewModelScope.launch {
            val result = tariffsUseCase.deleteTariff(tariff.id)
            if (result.data == true) {
                val tariffsList = state.value.data.toMutableList()
                if (tariffsList.remove(tariff)) {
                    _state.update {
                        it.copy(data = tariffsList)
                    }
                }
            } else {
                _state.update {
                    it.copy(message = result.message )
                }
            }
        }
    }

    fun addTariff(
        tariff: TariffModel
    ) {
        viewModelScope.launch {
            _editTariffState.update{
                it.copy(
                    isLoading = true,
                    message = "adding tariff..."
                )
            }
            val result = tariffsUseCase.addTariff(tariff)
            if (result.data != null) {
                val newTariffsList = state.value.data.toMutableList()
                newTariffsList.add(index = 0, element = result.data)
                _state.update {
                    it.copy(
                        data = newTariffsList
                    )
                }
                _editTariffState.update {
                    it.copy(
                        isLoading = false,
                        message = "completed"
                    )
                }
            } else {
                _editTariffState.update {
                    it.copy(
                        isLoading = false,
                        message = result.message
                    )
                }
            }
        }
    }
}