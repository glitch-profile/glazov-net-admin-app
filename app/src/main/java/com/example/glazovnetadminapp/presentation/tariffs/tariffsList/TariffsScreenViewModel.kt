package com.example.glazovnetadminapp.presentation.tariffs.tariffsList

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.glazovnetadminapp.domain.models.tariffs.TariffModel
import com.example.glazovnetadminapp.domain.models.tariffs.TariffType
import com.example.glazovnetadminapp.domain.useCases.TariffsUseCase
import com.example.glazovnetadminapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.internal.toImmutableList
import javax.inject.Inject

@HiltViewModel
class TariffsScreenViewModel @Inject constructor(
    private val tariffsUseCase: TariffsUseCase
): ViewModel() {

    private val _state = MutableStateFlow(TariffsScreenState())
    val state = _state.asStateFlow()

    private val _nameFilter = MutableStateFlow("")
    val nameFilter = _nameFilter.asStateFlow()

    val filteredTariffs = state
        .combine(nameFilter) {state, name ->
            if (name.isBlank()) {
                state.tariffsData
            } else {
                state.tariffsData.filter {
                    it.name.contains(name)
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            initialValue = listOf()
        )

    init {
        loadTariffs()
        //loadTariffsLocal()

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
                                tariffsData = result.data.filterNotNull(),
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

    @Deprecated("Use only without internet connection")
    fun loadTariffsLocal() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    tariffsData = mutableListOf()
                )
            }
            delay(500L)
            _state.update {
                it.copy(
                    tariffsData = listOf(
                        TariffModel(
                            id = "123",
                            name = "Like 100",
                            description = "This is some long description about this tariff. It has max speed to 100 Mbits/s only for 600 per month",
                            category = TariffType.Unlimited,
                            maxSpeed = 100,
                            costPerMonth = 600
                        ),
                        TariffModel(
                            id = "124",
                            name = "Like 60",
                            category = TariffType.Unlimited,
                            maxSpeed = 60,
                            costPerMonth = 500
                        ),
                        TariffModel(
                            id = "1223",
                            name = "Like 100",
                            description = "This is some long description about this tariff. It has max speed to 100 Mbits/s only for 600 per month",
                            category = TariffType.Unlimited,
                            maxSpeed = 100,
                            costPerMonth = 600
                        )
                    ),
                    isLoading = false,
                    message = null
                )
            }
            filteredTariffs.collectLatest {
                Log.i("TAG", "loadTariffsLocal: $it")

            }
        }
    }

    fun updateTariff(
        tariff: TariffModel
    ) {
        viewModelScope.launch {
            val result = tariffsUseCase.updateTariff(tariff = tariff)
            if (result.data == true) {
                val tariffIndex = state.value.tariffsData.indexOfFirst {
                    it.id == tariff.id
                }
                if (tariffIndex == -1) return@launch
                else {
                    val newTariffsList = state.value.tariffsData.toMutableList()
                    newTariffsList[tariffIndex] = tariff
                    _state.update {
                        it.copy(
                            tariffsData = newTariffsList
                        )
                    }
                }
            } else {
                _state.update {
                    it.copy(
                        message = result.message
                    )
                }
            }
        }
    }

    fun removeTariff(
        tariffId: String
    ) {
        viewModelScope.launch {
            val result = tariffsUseCase.deleteTariff(tariffId)
            Log.i("TAG", "removeTariff: db result - ${result.message}")
            if (result.data == true) {
                val tariffIndex = state.value.tariffsData.indexOfFirst {
                    it.id == tariffId
                }
                Log.i("TAG", "removeTariff: index to delete - $tariffIndex")
                if (tariffIndex == -1) return@launch
                else {
                    val newTariffsList = state.value.tariffsData.toMutableList()
                    newTariffsList.removeAt(tariffIndex)
                    _state.update {
                        it.copy(
                            tariffsData = newTariffsList
                        )
                    }
                }
            } else {
                _state.update {
                    it.copy(
                        message = result.message
                    )
                }
            }
        }
    }

    @Deprecated("Ony use without internet connection")
    fun removeTariffLocal(tariffId: String) {
        viewModelScope.launch {
            val tariffIndex = state.value.tariffsData.indexOfFirst {
                it.id == tariffId
            }
            if (tariffIndex == -1) {
                return@launch
            } else {
                val newTariffsList = state.value.tariffsData.toMutableList()
                newTariffsList.removeAt(tariffIndex)
                _state.update {
                    it.copy(
                        tariffsData = newTariffsList
                    )
                }
            }
        }
    }

    fun addTariff(
        tariff: TariffModel
    ) {
        viewModelScope.launch {
            val result = tariffsUseCase.addTariff(tariff)
            if (result.data == true) {
                val newTariffsList = state.value.tariffsData.toMutableList()
                newTariffsList.add(index = 0, element = tariff)
                _state.update {
                    it.copy(
                        tariffsData = newTariffsList
                    )
                }
            } else {
                _state.update {
                    it.copy(
                        message = result.message
                    )
                }
            }
        }
    }
}