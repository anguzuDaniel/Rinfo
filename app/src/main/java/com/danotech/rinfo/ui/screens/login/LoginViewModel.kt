package com.danotech.rinfo.ui.screens.login

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.danotech.rinfo.R
import com.danotech.rinfo.common.SnackbarManager
import com.danotech.rinfo.common.ext.isValidPassword
import com.danotech.rinfo.model.service.AccountService
import com.danotech.rinfo.model.service.LogService
import com.danotech.rinfo.model.service.impl.DataStoreUtil
import com.danotech.rinfo.model.service.impl.EmailValidator
import com.danotech.rinfo.ui.screens.RInfoScreen
import com.danotech.rinfo.ui.screens.RinfoViewModel
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject
constructor(
    private val dataStoreUtil: DataStoreUtil,
    private val accountService: AccountService,
    private val emailValidator: EmailValidator,
    logService: LogService,
) : RinfoViewModel(logService) {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                dataStoreUtil.store().data
                    .map { preferences ->
                        LoginUiState(
                            password = preferences[DataStoreUtil.USER_ID_KEY] ?: "",
                            email = preferences[DataStoreUtil.USER_EMAIL_KEY] ?: ""
                        )
                    }
                    .collect {
                        _uiState.value = it
                    }
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }


    fun isEmailValid(email: String): Boolean {
        return emailValidator.isValidEmail(email)
    }

    fun isPasswordValid(password: String): Boolean {
        return emailValidator.isValidPassword(password)
    }

    private val email: String
        get() = _uiState.value.email

    private val password: String
        get() = _uiState.value.password

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun signInClick(openAndPopUp: (String, String) -> Unit) {
        if (!isEmailValid(email)) {
            SnackbarManager.showMessage(R.string.email_error)
            _uiState.value = _uiState.value.copy(
                hasMessage = true,
                message = "Please insert a valid email."
            )
            return
        }

        if (!isPasswordValid(password)) {
            SnackbarManager.showMessage(R.string.password_error)
            _uiState.value = _uiState.value.copy(
                hasMessage = true,
                message = "Incorrect password!"
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isSignInLoading = true
            )

            try {
                if (accountService.checkUserExistsByEmail(email)) {
                    accountService.authenticate(email, password)
                    openAndPopUp(RInfoScreen.Home.name, RInfoScreen.Login.name)
                }
            } catch (e: Exception) {
                // Handle exceptions here
                _uiState.value = _uiState.value.copy(
                    isSignInSuccess = false,
                    hasMessage = true,
                    message = "Sign-in failed: ${e.message}"
                )
            } finally {
                _uiState.value = _uiState.value.copy(
                    isSignInLoading = false
                )
            }
        }.invokeOnCompletion {
            launchCatching {
                if (!accountService.checkUserExistsByEmail(email)) {
                    _uiState.value = _uiState.value.copy(
                        hasMessage = true,
                        isSignInLoading = false,
                        message = "User with email doesn't exist."
                    )
                }

                _uiState.value = _uiState.value.copy(
                    isSignInLoading = false,
                )
            }
        }
    }

    fun rememberUser(openAndPopUp: (String, String) -> Unit) {
        if (isEmailValid(email)) {
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
            openAndPopUp(RInfoScreen.Login.name, RInfoScreen.Home.name)
        }.invokeOnCompletion {
            _uiState.value = _uiState.value.copy(
                hasMessage = true,
                message = "Login successfully! kindly wait as we redirect you."
            )
        }
    }

    fun signUpWithOneTap(
        email: String,
        password: String?,
        openAndPopUp: (String, String) -> Unit
    ) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                accountService.linkAccount(email, password!!)
                _uiState.value = _uiState.value.copy(
                    isSignInSuccess = true,
                    hasMessage = true,
                    message = "Login successfully! kindly wait as we redirect you."
                )
                openAndPopUp(RInfoScreen.Home.name, RInfoScreen.Login.name)
            }
        }.invokeOnCompletion {
            _uiState.value = _uiState.value.copy(
                hasMessage = true,
                message = "Login successfully! kindly wait as we redirect you."
            )
        }
    }

    fun signInWithCredentials(
        credential: AuthCredential,
        openAndPopUp: (String, String) -> Unit
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSignInLoading = true)


            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(isSignInLoading = true)

                try {
                    val signedIn = withContext(Dispatchers.IO) {
                        accountService.signInWithCredential(credential)
                    }

                    if (signedIn) {
                        _uiState.value = _uiState.value.copy(
                            isSignInSuccess = true,
                            hasMessage = true,
                            message = "Sign-in with Google Successful."
                        )

                        openAndPopUp(RInfoScreen.Home.name, RInfoScreen.Login.name)
                    } else {
                        _uiState.value = _uiState.value.copy(
                            isSignInSuccess = false,
                            hasMessage = true,
                            message = "We were not able to sign you in."
                        )
                    }
                } catch (e: Exception) {
                    // Handle exceptions here
                    _uiState.value = _uiState.value.copy(
                        isSignInSuccess = false,
                        hasMessage = true,
                        message = "Sign-in failed: ${e.message}"
                    )
                } finally {
                    _uiState.value = _uiState.value.copy(
                        isSignInLoading = false
                    )
                }
            }
        }
    }

    private fun handleError(e: Exception) {
        // Log the error for debugging purposes
        Log.e(TAG, "An error occurred: ${e.message}", e)

        _uiState.value = _uiState.value.copy(
            isSignInLoading = false,
            hasMessage = true,
            message = "An error occurred: ${e.message}"
        )
    }
}


