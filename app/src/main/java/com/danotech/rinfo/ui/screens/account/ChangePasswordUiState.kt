package com.danotech.rinfo.ui.screens.account


data class ChangePasswordUiState(
    val newPassword: String = "",
    val confirmNewPassword: String = "",
    val hasMessage: Boolean = false,
    val message: String = "",
    val isLoading: Boolean = false
)