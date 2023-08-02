package com.danotech.rinfo

import androidx.lifecycle.ViewModel
import com.danotech.rinfo.ui.RinfoAppUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RinfoViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RinfoAppUiState())
    val uiState: StateFlow<RinfoAppUiState> = _uiState

    init {
        initializeUIState()
    }

    private fun initializeUIState() {
        _uiState.value = RinfoAppUiState()
    }
}