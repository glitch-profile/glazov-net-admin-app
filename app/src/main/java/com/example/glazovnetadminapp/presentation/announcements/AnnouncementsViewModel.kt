package com.example.glazovnetadminapp.presentation.announcements

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.glazovnetadminapp.domain.models.announcements.AddressFilterElement
import com.example.glazovnetadminapp.domain.models.announcements.AnnouncementModel
import com.example.glazovnetadminapp.domain.useCases.AnnouncementsUseCase
import com.example.glazovnetadminapp.presentation.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnnouncementsViewModel @Inject constructor(
    private val announcementsUseCase: AnnouncementsUseCase
): ViewModel() {

    private var _state = MutableStateFlow(ScreenState<AnnouncementModel>())
    val state = _state.asStateFlow()

    private var _announcementToEdit = MutableStateFlow<AnnouncementModel?>(null)
    val announcementToEdit = _announcementToEdit.asStateFlow()

    private var _citiesList = MutableStateFlow(emptyList<String>())
    val citiesList = _citiesList.asStateFlow()
    private var _streetsList = MutableStateFlow(emptyList<String>())
    val streetsList = _streetsList.asStateFlow()

    fun getCitiesList(
        cityName: String
    ) {
        viewModelScope.launch {
            val list = announcementsUseCase.getCitiesList(cityName)
            _citiesList.update { list }
        }
    }

    fun getStreetsList(
        cityName: String,
        streetName: String
    ) {
        viewModelScope.launch {
            val list = announcementsUseCase.getStreetsList(cityName, streetName)
            _streetsList.update { list }
        }
    }

}