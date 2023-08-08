package com.danotech.rinfo.ui.screens.settings

import androidx.compose.runtime.mutableStateOf
import com.danotech.rinfo.model.service.AccountService
import com.danotech.rinfo.model.service.LogService
import com.danotech.rinfo.ui.screens.RInfoScreen
import com.danotech.rinfo.ui.screens.RinfoViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val accountService: AccountService,
    logService: LogService
) : RinfoViewModel(logService) {
    val uiState = mutableStateOf(SettingsUiState())

    init {
        initializeUIState()
    }

    private fun initializeUIState() {
        uiState.value = SettingsUiState()
    }

    fun onLogoutClick(openAndPopUp: (String, String) -> Unit) {
        launchCatching {
            accountService.signOut()
            uiState.value = uiState.value.copy(isLogoutSuccess = true)
            openAndPopUp(RInfoScreen.Home.name, RInfoScreen.Settings.name)
        }
    }
}