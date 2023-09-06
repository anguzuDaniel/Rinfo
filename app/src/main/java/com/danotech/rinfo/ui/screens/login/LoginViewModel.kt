package com.danotech.rinfo.ui.screens.login

import com.danotech.rinfo.R
import com.danotech.rinfo.common.SnackbarManager
import com.danotech.rinfo.common.ext.isValidEmail
import com.danotech.rinfo.common.ext.isValidPassword
import com.danotech.rinfo.model.service.AccountService
import com.danotech.rinfo.model.service.LogService
import com.danotech.rinfo.ui.screens.RInfoScreen
import com.danotech.rinfo.ui.screens.RinfoViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountService: AccountService,
    logService: LogService
) : RinfoViewModel(logService) {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val email: String
        get() = _uiState.value.email

    private val password: String
        get() = _uiState.value.password

    fun onEmailChange(newValue: String) {
        _uiState.value = _uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        _uiState.value = _uiState.value.copy(password = newValue)
    }

    fun signInClick(openAndPopUp: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(R.string.email_error)
            _uiState.value = _uiState.value.copy(
                hasMessage = true,
                message = "Please insert a valid email."
            )
            return
        }

        if (!password.isValidPassword()) {
            SnackbarManager.showMessage(R.string.password_error)
            _uiState.value = _uiState.value.copy(
                hasMessage = true,
                message = "Incorrect password!"
            )
            return
        }

        launchCatching {
            accountService.authenticate(email, password)
            _uiState.value = _uiState.value.copy(
                isSignInSuccess = true,
                hasMessage = true,
                message = "Login successfully! kindly wait as we redirect you."
            )
            openAndPopUp(RInfoScreen.Home.name, RInfoScreen.Login.name)
        }.invokeOnCompletion {
            _uiState.value = _uiState.value.copy(
                hasMessage = true,
                message = "Login successfully! kindly wait as we redirect you."
            )
        }
    }
}