package com.danotech.rinfo.ui.screens.login

/**
 * This class represents the state of the UI for the Login screen.
 * @param email The email entered by the user.
 * @param password The password entered by the user.
 * @see LoginViewModel
 */
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    var message: String = "",
    val isSignInSuccess: Boolean = false,
    val hasMessage: Boolean = false
)