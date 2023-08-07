package com.danotech.rinfo.ui.screens.login

import androidx.compose.runtime.mutableStateOf
import com.danotech.rinfo.R
import com.danotech.rinfo.common.SnackbarManager
import com.danotech.rinfo.common.ext.isValidEmail
import com.danotech.rinfo.common.ext.isValidPassword
import com.danotech.rinfo.model.service.AccountService
import com.danotech.rinfo.model.service.LogService
import com.danotech.rinfo.ui.screens.RinfoViewModel
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val accountService: AccountService,
    logService: LogService
) : RinfoViewModel(logService) {
    val uiState = mutableStateOf(LoginUiState())

    private val email: String
        get() = uiState.value.email

    private val password: String
        get() = uiState.value.password


    init {
        initializeUIState()
    }

    private fun initializeUIState() {
        uiState.value = LoginUiState()
    }

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun signInClick() {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(R.string.email_error)
            return
        }

        if (!password.isValidPassword()) {
            SnackbarManager.showMessage(R.string.password_error)
            return
        }

        launchCatching {
            accountService.authenticate(email, password)
            uiState.value = uiState.value.copy(isSignInSuccess = true)
        }
    }
}