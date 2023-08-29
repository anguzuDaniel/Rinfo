package com.danotech.rinfo.ui.screens.profile

import androidx.compose.runtime.mutableStateOf
import com.danotech.rinfo.R
import com.danotech.rinfo.common.SnackbarManager
import com.danotech.rinfo.model.Profile
import com.danotech.rinfo.model.service.AccountService
import com.danotech.rinfo.model.service.LogService
import com.danotech.rinfo.model.service.ProfileService
import com.danotech.rinfo.ui.screens.RinfoViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileService: ProfileService,
    private val accountService: AccountService,
    logService: LogService
) : RinfoViewModel(logService) {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

//    val profile = launchCatching {
//        profileService.profiles.firstOrNull { it.profileId == accountService.currentUserId }
//    }

    init {
        initializeUIState()
    }

    private fun initializeUIState() {
        _uiState.value = ProfileUiState()
    }

    fun onProfileNameChanged(profileName: String) {
        _uiState.value = _uiState.value.copy(profileName = profileName)
    }

    fun onProfileFirstNameChanged(name: String) {
        _uiState.value = _uiState.value.copy(profileFirstName = name)
    }

    fun profileLastNameChanged(name: String) {
        _uiState.value = _uiState.value.copy(profileLastName = name)
    }

    fun profileImage(url: String) {
        _uiState.value = _uiState.value.copy(profileImage = url)
    }

    fun saveProfile() {
        _uiState.value = _uiState.value.copy(isLoading = true)

        launchCatching {
            val profile = Profile(
                profileName = _uiState.value.profileName,
                firstName = _uiState.value.profileFirstName,
                lastName = _uiState.value.profileLastName,
                profileImageUrl = _uiState.value.profileImage
            )
            if (profileService.getProfile(FirebaseAuth.getInstance().currentUser!!.email.toString()) == null) {
                profileService.create(profile)
                SnackbarManager.showMessage(R.string.profile_added)
            } else {
                profileService.update(profile)
                SnackbarManager.showMessage(R.string.profile_updated)
            }
        }.invokeOnCompletion {
            getProfile()
            _uiState.value = uiState.value.copy(isLoading = false)
        }
    }

    fun getProfile() {
        launchCatching {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val profile =
                profileService.getProfile(FirebaseAuth.getInstance().currentUser!!.email.toString())

            if (profile != null) {
                _uiState.value = _uiState.value.copy(
                    profileName = profile.profileName,
                    profileFirstName = profile.firstName,
                    profileLastName = profile.lastName,
                    profileImage = profile.profileImageUrl
                )
            }
        }.invokeOnCompletion {
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }
}