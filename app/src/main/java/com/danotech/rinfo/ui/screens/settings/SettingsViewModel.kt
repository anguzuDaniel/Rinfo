package com.danotech.rinfo.ui.screens.settings

import com.danotech.rinfo.model.service.AccountService
import com.danotech.rinfo.model.service.LogService
import com.danotech.rinfo.model.service.ProfileService
import com.danotech.rinfo.ui.screens.RInfoScreen
import com.danotech.rinfo.ui.screens.RinfoViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val profileService: ProfileService,
    private val accountService: AccountService,
    logService: LogService
) : RinfoViewModel(logService) {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState = _uiState.asStateFlow()

    fun onLogoutClick(openAndPopUp: (String, String) -> Unit) {
        launchCatching {
            accountService.signOut()
            _uiState.value = _uiState.value.copy(isLogoutSuccess = true)
            openAndPopUp(RInfoScreen.Home.name, RInfoScreen.Settings.name)
        }
    }

    fun onAccountClick(openAndPopUp: (String, String) -> Unit) {
        openAndPopUp(RInfoScreen.Account.name, RInfoScreen.Settings.name)
    }

    fun getProfile() {
        launchCatching {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val profile =
                profileService.getProfile(FirebaseAuth.getInstance().currentUser!!.email.toString())

            if (profile != null) {
                _uiState.value = _uiState.value.copy(
                    profileName = profile.profileName,
                    profileEmail = profile.profileName,
                    profile = profile.profileImageUrl
                )
            }
        }.invokeOnCompletion {
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }
}