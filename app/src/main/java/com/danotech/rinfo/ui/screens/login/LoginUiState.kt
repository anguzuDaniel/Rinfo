package com.danotech.rinfo.ui.screens.login

/**
 * This class represents the state of the UI for the Login screen.
 * @param email The email entered by the user.
 * @param password The password entered by the user.
 * @param message The message or feedback given to shown to the user
 * @param isSignInSuccess a boolean to check if login is successful
 * @param hasMessage a boolean that is true message is available and false when not present
 * @param isSignInLoading a boolean shows if the sign in is successful
 * @see LoginViewModel
 */
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    var message: String = "",
    val isSignInSuccess: Boolean = false,
    val hasMessage: Boolean = false,
    val isSignInLoading: Boolean = false,
)