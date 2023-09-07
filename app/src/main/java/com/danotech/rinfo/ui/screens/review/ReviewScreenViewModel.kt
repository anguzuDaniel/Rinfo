package com.danotech.rinfo.ui.screens.review

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.MutableState
import com.danotech.rinfo.model.Business
import com.danotech.rinfo.model.service.LogService
import com.danotech.rinfo.model.service.ProfileService
import com.danotech.rinfo.model.service.ReviewService
import com.danotech.rinfo.ui.screens.RinfoViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class ReviewScreenViewModel
@Inject
constructor(
    private val profileService: ProfileService,
    private val reviewService: ReviewService,
    logService: LogService
) :
    RinfoViewModel(logService) {
    private val _uiState =
        MutableStateFlow(
            ReviewUiState(
                isLoading = false,
                imageLoading = false,
                businessId = Business().id
            )
        )
    val uiState = _uiState.asStateFlow()

    fun getUserNameById(userId: String) {
        launchCatching {
            try {
                val profileName = profileService.getProfile(userId)!!.profileName
                _uiState.value = _uiState.value.copy(reviewUserName = profileName)
            } catch (e: Exception) {
                // Handle error
                _uiState.value = _uiState.value.copy(isLoading = true)
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun getProfileImage(
        userId: String,
        bitmap: MutableState<Bitmap>
    ) {
        launchCatching {
            _uiState.value = _uiState.value.copy(imageLoading = true)

            val storageRef = Firebase.storage.reference
            val imageRef = storageRef.child("logos/${userId}.jpg")

            val ONE_MEGABYTE: Long = 1024 * 1024
            imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener {
                val bmp: Bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                bitmap.value = bmp
            }.addOnFailureListener {
                // Handle any errors
            }
        }.invokeOnCompletion {
            _uiState.value = _uiState.value.copy(imageLoading = false)
        }

    }

    fun deleteReview(reviewId: String, businessId: String) {
        launchCatching {
            try {
                reviewService.delete(reviewId)

                reviewService.startListeningToReviewsByBusinessId(businessId)
                val reviewList = reviewService.getAllReviews().first()
                _uiState.value = _uiState.value.copy(reviews = reviewList)
            } catch (e: Exception) {
                // Handle error
                _uiState.value = _uiState.value.copy(isLoading = true)
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun getReviewByBusinessId(businessId: String) {
        launchCatching {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                reviewService.startListeningToReviewsByBusinessId(businessId)

                val reviewsList = reviewService.getAllReviews().first()
                _uiState.value = _uiState.value.copy(reviews = reviewsList)
            } catch (e: Exception) {
                // Handle error
                _uiState.value = _uiState.value.copy(isLoading = true)
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }.invokeOnCompletion {
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }
}