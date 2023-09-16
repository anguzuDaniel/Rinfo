package com.danotech.rinfo.ui.screens

import androidx.lifecycle.viewModelScope
import com.danotech.rinfo.data.preferences.utils.DataStoreUtil
import com.danotech.rinfo.model.service.AccountService
import com.danotech.rinfo.model.service.LogService
import com.danotech.rinfo.ui.screens.login.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel
@Inject
constructor(
    private val accountService: AccountService,
    private val dataStoreUtil: DataStoreUtil,
    logService: LogService
) : RinfoViewModel(logService) {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    // Define a LiveData or StateFlow to track the user's login state.
    private val _isUserLoggedIn = MutableStateFlow(false)
    val isUserLoggedIn = _isUserLoggedIn.asStateFlow()

    // Add a function to check if the user is logged in.
    fun checkUserLoggedIn() {
        viewModelScope.launch {
            val userId = dataStoreUtil.readUserId() // Implement this function in DataStoreUtil
            _isUserLoggedIn.value = !userId.isNullOrEmpty()
        }
    }

    // Add a function to log the user out.
    fun logOut() {
        viewModelScope.launch {
            // Implement logout logic here, e.g., clear user data from DataStore.
            dataStoreUtil.clearUserData() // Implement this function in DataStoreUtil

            // Update the login state to indicate that the user is logged out.
            _isUserLoggedIn.value = false
        }
    }

    // Implement login logic as needed.
    // You can use the launchCatching function for error handling.

    // Example login function:
    fun login(username: String, password: String, openAndPopUp: (String, String) -> Unit) {
        launchCatching {
            // Implement your login logic here.
            // If login is successful, update the user's login state.
            val loggedIn = performLogin(username, password) // Implement this function
            if (loggedIn) {
                _isUserLoggedIn.value = true
                openAndPopUp(RInfoScreen.Home.name, RInfoScreen.Login.name)
            }
        }
    }

    private fun performLogin(email: String, password: String): Boolean {
        var loginSuccess = false

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSignInLoading = true)

            try {
                val authResult = accountService.authenticate(email, password)
                // The user is successfully logged in, you can handle it accordingly.
                loginSuccess = true
            } catch (e: Exception) {
                // Handle login failure, e.g., display an error message.
                _uiState.value =
                    _uiState.value.copy(hasMessage = true, message = "Login failed: ${e.message}")
            }
        }.invokeOnCompletion {
            viewModelScope.launch {
                if (!accountService.checkUserExistsByEmail(email)) {
                    _uiState.value = _uiState.value.copy(
                        hasMessage = true,
                        isSignInLoading = false,
                        message = "User with email doesn't exist."
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isSignInSuccess = true,
                        hasMessage = true,
                        isSignInLoading = false,
                        message = "Login successfully! kindly wait as we redirect you."
                    )
                }
            }
        }

        return loginSuccess
    }
}
