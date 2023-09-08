package com.danotech.rinfo.ui.screens.reset_password

import androidx.lifecycle.viewModelScope
import com.danotech.rinfo.RinfoViewModel
import com.danotech.rinfo.common.ext.isValidPassword
import com.danotech.rinfo.model.service.AccountService
import com.danotech.rinfo.model.service.LogService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel
@Inject
constructor(
    private val accountService: AccountService,
    logService: LogService
) : RinfoViewModel(logService) {
    private val _uiState = MutableStateFlow(ResetPasswordUiState())
    val uiState = _uiState.asStateFlow()

    fun onPasswordChange(password: String) {
        if (password.isEmpty()) {
            _uiState.value = _uiState.value.copy(message = "Please enter a valid password.")
            return
        }
        _uiState.value = _uiState.value.copy(newPassword = password)
    }


    fun onEmailChanged(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun requestRestPasswordLink() {
        val email = _uiState.value.email

        if (email.isEmpty() && !email.isValidPassword()) {
            _uiState.value =
                _uiState.value.copy(hasError = true, message = "Please enter a valid email.")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val hasAccount = accountService.checkUserExistsByEmail(email)

            if (hasAccount) {
                accountService.sendRecoveryEmail(email)
                _uiState.value = _uiState.value.copy(
                    hasMessage = true,
                    message = "Please check your email for the password reset link."
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    hasError = true,
                    hasMessage = false,
                    message = "User does not exist, please create an account."
                )
            }
        }.invokeOnCompletion {
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }
}