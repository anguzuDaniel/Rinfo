package com.danotech.rinfo.ui.screens.account

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class CreateAccountViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CreateAccountUiState())
    val uiState: MutableStateFlow<CreateAccountUiState> = _uiState

    init {
        initializeUIState()
    }

    private fun initializeUIState() {
        _uiState.value = CreateAccountUiState()
    }

    /**
     * This function is called when the user changes the email field.
     * @param email The email the user entered.
     * @see CreateAccountUiState
     */
    fun onEmailChanged(email: String) {
        _uiState.update {
            it.copy(email = email)
        }
    }

    /**
     * This function is called when the user changes the password field.
     * @param password The password the user entered.
     * @see CreateAccountUiState
     */
    fun onPasswordChanged(password: String) {
        _uiState.update {
            it.copy(password = password)
        }
    }

    /**
     * This function is called when the user input in the confirm password field.
     * @param confirmPassword
     * @see CreateAccount
     */
    fun onConfirmPasswordChanged(confirmPassword: String) {
        _uiState.update {
            it.copy(confirmPassword = confirmPassword)
        }
    }

    /**
     * This function is called when the user inputs in the first name
     * @param firstName
     */
    fun onFirstNameChanged(firstName: String) {
        _uiState.update {
            it.copy(firstName = firstName)
        }
    }

    /**
     * This function is called when the user inputs the last name
     * @param lastName
     */
    fun onLastNameChanged(lastName: String) {
        _uiState.update {
            it.copy(lastName = lastName)
        }
    }

    /**
     * This function is create account button is clicked
     */
    fun onCreateAccountClicked() {
        _uiState.update {
            it.copy(isCreateAccountInProgress = true)
        }
    }

    /**
     * when account is created successfully
     */
    fun onAccountCreated() {
        _uiState.update {
            it.copy(isCreateAccountInProgress = false, isCreateAccountSuccess = true)
        }
    }

    /**
     * when account creation fails
     */
    fun onAccountCreationFailed() {
        _uiState.update {
            it.copy(isCreateAccountInProgress = false, isCreateAccountError = true)
        }
    }

    /**
     * show when there is an error
     */
    fun onAccountCreationErrorShown() {
        _uiState.update {
            it.copy(isCreateAccountError = false)
        }
    }

    /**
     * show when account creation is successful
     */
    fun onAccountCreationSuccessShown() {
        _uiState.update {
            it.copy(isCreateAccountSuccess = false)
        }
    }

    /**
     * when account type is selected
     * @param accountType
     */
    fun onAccountTypeSelected(accountType: AccountType) {
        _uiState.update {
            it.copy(accountType = accountType)
        }
    }
}