package com.danotech.rinfo.ui.screens.account

import androidx.annotation.StringRes

/**
 * A data class representing the state of the create account screen.
 * @param name The first name of the user.
 * @param lastName The last name of the user.
 * @param email The email of the user.
 * @param password The password of the user.
 * @param confirmPassword The password confirmation of the user.
 * @param accountType The type of account the user is creating.
 * @param isCreateAccountButtonEnabled Whether the create account button is enabled.
 * @param isCreateAccountInProgress Whether the create account process is in progress.
 * @param isCreateAccountSuccess Whether the create account process is successful.
 * @param isCreateAccountError Whether the create account process has failed.
 * @see AccountType
 * @see CreateAccountScreen
 * @see CreateAccountViewModel
 * @see CreateAccountViewModelFactory
 */
data class CreateAccountUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val accountType: AccountType = AccountType(0, 0, 0),
    val isCreateAccountButtonEnabled: Boolean = false,
    val isCreateAccountInProgress: Boolean = false,
    val isCreateAccountSuccess: Boolean = false,
    val isCreateAccountError: Boolean = false,
    var errorMessage: String = "",
    var isLoading: Boolean = false
)

/**
 * A data class representing an account type.
 */
data class AccountType(
    val id: Int,
    @StringRes val name: Int,
    val icon: Int,
)