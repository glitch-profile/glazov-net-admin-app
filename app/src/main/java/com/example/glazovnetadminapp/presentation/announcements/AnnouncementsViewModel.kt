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

    private var _citiesSearchText = MutableStateFlow("")
    val citiesSearchText = _citiesSearchText.asStateFlow()
    private var _streetsSearchText = MutableStateFlow("")
    val streetsSearchText = _streetsSearchText.asStateFlow()

    init {
        _citiesSearchText.debounce(3000L)
        _streetsSearchText.debounce(300L)
    }

}