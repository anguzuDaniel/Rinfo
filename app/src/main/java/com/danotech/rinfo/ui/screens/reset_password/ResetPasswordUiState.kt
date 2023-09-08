package com.danotech.rinfo.ui.screens.reset_password

data class ResetPasswordUiState(
    val newPassword: String = "",
    val email: String = "",
    val message: String = "",
    val hasError: Boolean = false,
    var hasMessage: Boolean = false,
    var isLoading: Boolean = false,
    val hasAccount: Boolean = false
)
