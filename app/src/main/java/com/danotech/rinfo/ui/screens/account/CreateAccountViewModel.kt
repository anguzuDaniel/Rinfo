package com.danotech.rinfo.ui.screens.account

import com.danotech.rinfo.RinfoViewModel
import com.danotech.rinfo.common.SnackbarManager
import com.danotech.rinfo.common.ext.isValidEmail
import com.danotech.rinfo.common.ext.isValidPassword
import com.danotech.rinfo.common.ext.passwordMatches
import com.danotech.rinfo.model.service.AccountService
import com.danotech.rinfo.model.service.LogService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import com.danotech.rinfo.R.string as AppText

@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    private val accountService: AccountService,
    logService: LogService
) : RinfoViewModel(logService) {
    private val _uiState = MutableStateFlow(CreateAccountUiState())
    val uiState = _uiState.asStateFlow()

    private val email: String
        get() = _uiState.value.email

    private val password: String
        get() = _uiState.value.password

    /**
     * This function is called when the user changes the email field.
     * @param email The email the user entered.
     * @see CreateAccountUiState
     */
    fun onEmailChanged(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    /**
     * This function is called when the user changes the password field.
     * @param password The password the user entered.
     * @see CreateAccountUiState
     */
    fun onPasswordChanged(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    /**
     * This function is called when the user input in the confirm password field.
     * @param confirmPassword
     * @see CreateAccount
     */
    fun onConfirmPasswordChanged(confirmPassword: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = confirmPassword)
    }

    /**
     * This function is called when the user inputs in the first name
     * @param name
     */
    fun onNameChanged(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    /**
     * This function is create account button is clicked
     */
    fun onCreateAccountClicked() {
        _uiState.value = _uiState.value.copy(isCreateAccountInProgress = true)
    }

    /**
     * when account is created successfully
     */
    fun onAccountCreated() {
        _uiState.value = _uiState.value.copy(
            isCreateAccountInProgress = false, isCreateAccountSuccess = true
        )
    }

    /**
     * when account creation fails
     */
    fun onAccountCreationFailed() {
        _uiState.value =
            _uiState.value.copy(isCreateAccountInProgress = false, isCreateAccountError = true)
    }

    /**
     * show when there is an error
     */
    fun onAccountCreationErrorShown() {
        _uiState.value = _uiState.value.copy(isCreateAccountError = false)
    }

    /**
     * show when account creation is successful
     */
    fun onAccountCreationSuccessShown() {
        _uiState.value = _uiState.value.copy(isCreateAccountSuccess = false)
    }

    /**
     * when account type is selected
     * @param accountType
     */
    fun onAccountTypeSelected(accountType: AccountType) {
        _uiState.value = _uiState.value.copy(accountType = accountType)
    }

    fun onSignUpClick() {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            _uiState.value = _uiState.value.copy(
                isCreateAccountError = true, errorMessage = "Please enter a valid email!"
            )
            return
        }

        if (!password.isValidPassword()) {
            SnackbarManager.showMessage(AppText.password_error)
            _uiState.value = _uiState.value.copy(
                isCreateAccountError = true, errorMessage = "Please enter a valid email!"
            )
            return
        }

        if (!password.passwordMatches(uiState.value.confirmPassword)) {
            SnackbarManager.showMessage(AppText.password_match_error)
            _uiState.value = _uiState.value.copy(
                isCreateAccountError = true, errorMessage = "Please enter a valid email!"
            )
            return
        }

        launchCatching {
            accountService.createAccountWithEmailAndPassword(email, password)
            _uiState.value = _uiState.value.copy(isLoading = true)
        }.invokeOnCompletion {
            _uiState.value =
                _uiState.value.copy(errorMessage = "You are ready to login !", isLoading = false)
        }
    }
}