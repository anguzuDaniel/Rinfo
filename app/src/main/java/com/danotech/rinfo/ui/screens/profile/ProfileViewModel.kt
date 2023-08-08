package com.danotech.rinfo.ui.screens.profile

import androidx.compose.runtime.mutableStateOf
import com.danotech.rinfo.model.Profile
import com.danotech.rinfo.model.service.AccountService
import com.danotech.rinfo.model.service.LogService
import com.danotech.rinfo.model.service.ProfileService
import com.danotech.rinfo.ui.screens.RinfoViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileService: ProfileService,
    private val accountService: AccountService,
    logService: LogService
) : RinfoViewModel(logService) {
    val uiState = mutableStateOf(ProfileUiState())

//    val profile = launchCatching {
//        profileService.profiles.firstOrNull { it.profileId == accountService.currentUserId }
//    }

    init {
        initializeUIState()
    }

    private fun initializeUIState() {
        uiState.value = ProfileUiState()
    }

    fun onProfileNameChanged(profileName: String) {
        uiState.value = uiState.value.copy(profileName = profileName)
    }

    fun onProfileFirstNameChanged(name: String) {
        uiState.value = uiState.value.copy(profileFirstName = name)
    }

    fun profileLastNameChanged(name: String) {
        uiState.value = uiState.value.copy(profileLastName = name)
    }

    fun profileImage(url: String) {
        uiState.value = uiState.value.copy(profileImage = url)
    }

    fun saveProfile() {
        launchCatching {
            val profile = Profile(
                profileName = uiState.value.profileName,
                firstName = uiState.value.profileFirstName,
                lastName = uiState.value.profileLastName,
                profileImageUrl = uiState.value.profileImage
            )
            if (profileService.getProfile(FirebaseAuth.getInstance().currentUser!!.email.toString()) == null) {
                profileService.create(profile)
            } else {
                profileService.update(profile)
            }
        }
    }

    fun getProfile() {
        launchCatching {
            profileService.getProfile(accountService.currentUserId)?.let {
                uiState.value = uiState.value.copy(
                    profileName = it.profileName,
                    profileFirstName = it.firstName,
                    profileLastName = it.lastName,
                    profileImage = it.profileImageUrl
                )
            }
        }
    }
}