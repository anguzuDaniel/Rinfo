package com.danotech.rinfo.ui.screens.profile

import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth

/**
 *
 */
data class ProfileUiState(
    val profileId: String = FirebaseAuth.getInstance().currentUser!!.email.toString(),
    val profileImage: String = "",
    val profileImageBitmap: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888),
    val profileName: String = "",
    val profileFirstName: String = "",
    val profileLastName: String = "",
    val isLoading: Boolean = false,
    var showBottomSheet: Boolean = false,
)