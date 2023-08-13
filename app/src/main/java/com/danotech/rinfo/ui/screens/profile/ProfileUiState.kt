package com.danotech.rinfo.ui.screens.profile

import com.google.firebase.auth.FirebaseAuth

/**
 *
 */
data class ProfileUiState(
    val profileId: String = FirebaseAuth.getInstance().currentUser!!.email.toString(),
    val profileImage: String = "",
    val profileName: String = "",
    val profileFirstName: String = "",
    val profileLastName: String = "",
    val isLoading: Boolean = false,
)