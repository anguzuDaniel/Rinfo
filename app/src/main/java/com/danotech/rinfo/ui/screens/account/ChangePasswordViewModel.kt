package com.danotech.rinfo.ui.screens.account

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.danotech.rinfo.RinfoViewModel
import com.danotech.rinfo.common.ext.isValidPassword
import com.danotech.rinfo.model.service.AccountService
import com.danotech.rinfo.model.service.LogService
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthActionCodeException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel
@Inject
constructor(
    private val accountService: AccountService,
    logService: LogService
) : RinfoViewModel(logService) {
    private val _uiState = MutableStateFlow(ChangePasswordUiState())
    val uiState = _uiState.asStateFlow()

    fun onPasswordChange(password: String) {
        if (password.isEmpty()) {
            _uiState.value = _uiState.value.copy(message = "Please enter a valid password.")
            return
        }
        _uiState.value = _uiState.value.copy(newPassword = password)
    }

    fun requestRestPasswordLink() {
        val email = FirebaseAuth.getInstance().currentUser!!.email

        if (email != null) {
            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(isLoading = true)
                val hasAccount = accountService.checkUserExistsByEmail(email)

                if (hasAccount) {
                    accountService.sendRecoveryEmail(email)
                    _uiState.value = _uiState.value.copy(
                        hasMessage = true,
                        message = "You have changed your password."
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        hasMessage = false,
                        message = "Something went wrong please"
                    )
                }
            }.invokeOnCompletion {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    // Function to send a password reset email with an oob code
    fun sendPasswordResetEmail(email: String) {
        // Configure the action code settings (you can customize this)
        val actionCodeSettings = ActionCodeSettings.newBuilder()
            .setUrl("https://rinfo-5ee97.firebaseapp.com/__/auth/action?mode=action&oobCode=code") // URL to open when the user clicks the link
            .setHandleCodeInApp(true) // Open the link in your app
            .setAndroidPackageName(
                "com.yourapp.package",
                false, /* Install if not available? */
                "12" /* Minimum app version */
            )
            .build()

        // Send the password reset email
        FirebaseAuth.getInstance()
            .sendPasswordResetEmail(email, actionCodeSettings)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Password reset email sent successfully
                    Log.d("PasswordReset", "Password reset email sent successfully")
                } else {
                    // Password reset email failed to send
                    val exception = task.exception
                    if (exception is FirebaseAuthActionCodeException) {
                        // Invalid action code or expired link
                        Log.e("PasswordReset", "Invalid action code or expired link")
                    } else {
                        // Other error occurred
                        Log.e("PasswordReset", "Password reset email failed to send", exception)
                    }
                }
            }
    }
}