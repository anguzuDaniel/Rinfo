package com.danotech.rinfo.ui.screens.account

import androidx.compose.runtime.mutableStateOf
import com.danotech.rinfo.RinfoViewModel
import com.danotech.rinfo.common.SnackbarManager
import com.danotech.rinfo.common.ext.isValidEmail
import com.danotech.rinfo.common.ext.isValidPassword
import com.danotech.rinfo.common.ext.passwordMatches
import com.danotech.rinfo.model.service.AccountService
import com.danotech.rinfo.model.service.LogService
import com.danotech.rinfo.ui.SETTINGS_SCREEN
import com.danotech.rinfo.ui.SIGN_UP_SCREEN
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.danotech.rinfo.R.string as AppText

@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    private val accountService: AccountService,
    logService: LogService
) : RinfoViewModel(logService) {
    var uiState = mutableStateOf(CreateAccountUiState())
        private set

    private val email: String
        get() = uiState.value.email

    private val password: String
        get() = uiState.value.password

    init {
        initializeUIState()
    }

    private fun initializeUIState() {
        uiState.value = CreateAccountUiState()
    }

    /**
     * This function is called when the user changes the email field.
     * @param email The email the user entered.
     * @see CreateAccountUiState
     */
    fun onEmailChanged(email: String) {
        uiState.value = uiState.value.copy(email = email)
    }

    /**
     * This function is called when the user changes the password field.
     * @param password The password the user entered.
     * @see CreateAccountUiState
     */
    fun onPasswordChanged(password: String) {
        uiState.value = uiState.value.copy(password = password)
    }

    /**
     * This function is called when the user input in the confirm password field.
     * @param confirmPassword
     * @see CreateAccount
     */
    fun onConfirmPasswordChanged(confirmPassword: String) {
        uiState.value = uiState.value.copy(confirmPassword = confirmPassword)
    }

    /**
     * This function is called when the user inputs in the first name
     * @param name
     */
    fun onFirstNameChanged(name: String) {
        uiState.value = uiState.value.copy(name = name)
    }

    /**
     * This function is create account button is clicked
     */
    fun onCreateAccountClicked() {
        uiState.value = uiState.value.copy(isCreateAccountInProgress = true)
    }

    /**
     * when account is created successfully
     */
    fun onAccountCreated() {
        uiState.value = uiState.value.copy(isCreateAccountInProgress = false, isCreateAccountSuccess = true)
    }

    /**
     * when account creation fails
     */
    fun onAccountCreationFailed() {
        uiState.value = uiState.value.copy(isCreateAccountInProgress = false, isCreateAccountError = true)
    }

    /**
     * show when there is an error
     */
    fun onAccountCreationErrorShown() {
        uiState.value = uiState.value.copy(isCreateAccountError = false)
    }

    /**
     * show when account creation is successful
     */
    fun onAccountCreationSuccessShown() {
        uiState.value = uiState.value.copy(isCreateAccountSuccess = false)
    }

    /**
     * when account type is selected
     * @param accountType
     */
    fun onAccountTypeSelected(accountType: AccountType) {
        uiState.value = uiState.value.copy(accountType = accountType)
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        if (!password.isValidPassword()) {
            SnackbarManager.showMessage(AppText.password_error)
            return
        }

        if (!password.passwordMatches(uiState.value.confirmPassword)) {
            SnackbarManager.showMessage(AppText.password_match_error)
            return
        }

        launchCatching {
            accountService.linkAccount(email, password)
            openAndPopUp(SETTINGS_SCREEN, SIGN_UP_SCREEN)
        }
    }
}