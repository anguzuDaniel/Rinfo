package com.danotech.rinfo.ui.screens.settings

data class SettingsUiState(
    val profile: String = "",
    val profileName: String = "",
    val profileEmail: String = "",
    val isLoading: Boolean = false,
    val isDarkTheme: Boolean = false,
    val isLogoutSuccess: Boolean = false,
    val hasMessage: Boolean = false,
    val message: String = ""
)