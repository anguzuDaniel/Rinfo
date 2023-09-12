package com.danotech.rinfo.ui.screens.profile

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.danotech.rinfo.R
import com.danotech.rinfo.common.SnackbarManager
import com.danotech.rinfo.model.Profile
import com.danotech.rinfo.model.service.AccountService
import com.danotech.rinfo.model.service.LogService
import com.danotech.rinfo.model.service.ProfileService
import com.danotech.rinfo.ui.screens.RinfoViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileService: ProfileService,
    private val accountService: AccountService,
    logService: LogService
) : RinfoViewModel(logService) {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    fun onProfileNameChanged(profileName: String) {
        _uiState.value = _uiState.value.copy(profileName = profileName)
    }

    fun onProfileFirstNameChanged(name: String) {
        _uiState.value = _uiState.value.copy(profileFirstName = name)
    }

    fun profileLastNameChanged(name: String) {
        _uiState.value = _uiState.value.copy(profileLastName = name)
    }

    fun openBottomSheet(open: Boolean) {
        _uiState.value = _uiState.value.copy(showBottomSheet = open)
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

    fun getImageFromFireBase(userId: String) {
        launchCatching {
            _uiState.value = _uiState.value.copy(imageLoading = true)
            val storageRef = Firebase.storage.reference
            val imageRef = storageRef.child("logos/${userId}.jpg")

            val ONE_MEGABYTE: Long = 1024 * 1024
            imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener {
                val bmp: Bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                _uiState.value = _uiState.value.copy(profileImageBitmap = bmp)
            }.addOnFailureListener {
                // Handle any errors
            }
        }.invokeOnCompletion {
            _uiState.value = _uiState.value.copy(imageLoading = false)
        }
    }

    /**
     * Uploads image to firebase storage
     * @param image
     * uses the user id as the name of the image
     */
    fun upLoadImageToFireBase(image: Bitmap, userId: String) {
        // Inside your Composable function
        val storageRef = Firebase.storage.reference

        launchCatching {
            // sets loading to true
            _uiState.value = _uiState.value.copy(isLoading = true)

            val imageName = "${userId}_${System.currentTimeMillis()}.jpg"
            val imageRef = storageRef.child("logos/${imageName}")

            val stream = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.JPEG, 100, stream)

            val data = stream.toByteArray()
            val uploadTask = imageRef.putBytes(data)

            uploadTask.addOnSuccessListener {
                SnackbarManager.showMessage(R.string.image_uploaded_successfully)
            }.addOnFailureListener {
                // Handle upload failure
                SnackbarManager.showMessage(R.string.something_went_wrong)
            }

            _uiState.value = _uiState.value.copy(profileImage = data.toString())
        }.invokeOnCompletion {
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }
}