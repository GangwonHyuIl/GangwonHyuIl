package com.gangwonhyuil.gangwonhyuil.ui.home

import com.gangwonhyuil.gangwonhyuil.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel() {
    private val _selectedLocation = MutableStateFlow("강릉")
    val selectedLocation: StateFlow<String> get() = _selectedLocation

    fun updateSelectedLocation(location: String) {
        _selectedLocation.value = location

    }
}