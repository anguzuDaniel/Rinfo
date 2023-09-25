package com.danotech.rinfo.ui.screens.review

import androidx.lifecycle.viewModelScope
import com.danotech.rinfo.R
import com.danotech.rinfo.common.snackbar.SnackbarManager
import com.danotech.rinfo.model.Review
import com.danotech.rinfo.model.service.LogService
import com.danotech.rinfo.model.service.ProfileService
import com.danotech.rinfo.model.service.ReviewService
import com.danotech.rinfo.ui.screens.RinfoViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewFormViewModel @Inject constructor(
    private val reviewService: ReviewService,
    private val profileService: ProfileService,
    logService: LogService
) : RinfoViewModel(logService) {
    private val _uiState = MutableStateFlow(ReviewFormUiState())
    val uiState = _uiState.asStateFlow()

    // Function to reset state when entering edit mode
    private fun resetStateForEdit() {
        // Reset the necessary state variables to initial values for editing
        val initialUiState = ReviewFormUiState() // You need to define this appropriately
        _uiState.value = initialUiState
    }

    fun onTitleInput(title: String) {
        _uiState.value = _uiState.value.copy(title = title)
    }

    fun onReviewInput(review: String) {
        _uiState.value = _uiState.value.copy(review = review)
    }

    fun onDateInput(date: String) {
        _uiState.value = _uiState.value.copy(date = date)
    }

    fun addBusinessIdInformation(reviewedBusinessId: String, reviewerUserId: String) {
        _uiState.value = _uiState.value.copy(
            reviewedBusinessId = reviewedBusinessId,
            reviewerUserId = reviewerUserId
        )
    }

    fun onRatingChanged(rating: Int) {
        _uiState.value = _uiState.value.copy(
            rating = rating
        )
    }

    fun getReview(reviewId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = _uiState.value.copy(
                isLoading = true
            )

            val review = reviewService.getReviewById(reviewId)

            _uiState.value = _uiState.value.copy(
                title = review.title,
                review = review.review,
                rating = review.rating,
                name = review.name,
                reviewedBusinessId = review.reviewedBusinessId,
                reviewerUserId = review.reviewerUserId,
                profileUrl = review.userImageUrl,
            )
        }.invokeOnCompletion {
            _uiState.value = _uiState.value.copy(
                isLoading = false
            )
        }
    }

    fun resetState() {
        _uiState.value = _uiState.value.copy(
            title = "",
            review = "",
            rating = 0,
            isPositive = false,
            submitButtonEnabled = false,
            date = "",
            isLoading = false
        )
    }

    fun getProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val profile =
                    profileService.getProfile(FirebaseAuth.getInstance().currentUser!!.email.toString())

                if (profile != null) {
                    _uiState.value = _uiState.value.copy(
                        name = profile.profileName,
                        profileUrl = profile.profileImageUrl
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    message = "Only user that have profiles can review",
                )
            }
        }
    }

    fun updateReview(reviewId: String) {
        if (_uiState.value.title.isEmpty()) {
            SnackbarManager.showMessage(R.string.review_title_empty)
            return
        }

        if (_uiState.value.review.isEmpty()) {
            SnackbarManager.showMessage(R.string.review_empty)
            return
        }

        _uiState.value = uiState.value.copy(
            isPositive = uiState.value.rating > 3,
        )

        launchCatching {
            _uiState.value = uiState.value.copy(
                isLoading = true
            )

            reviewService.update(
                Review(
                    id = reviewId,
                    reviewedBusinessId = _uiState.value.reviewedBusinessId,
                    reviewerUserId = _uiState.value.reviewerUserId,
                    title = _uiState.value.title,
                    rating = _uiState.value.rating,
                    review = _uiState.value.review,
                    postive = _uiState.value.isPositive,
                    name = _uiState.value.name,
                    date = _uiState.value.date,
                    edited = true
                )
            )

            resetStateForEdit()
        }.invokeOnCompletion {
            _uiState.value = _uiState.value.copy(
                isLoading = false
            )
            SnackbarManager.showMessage(R.string.review_updated)
        }
    }

    fun addReview() {
        if (_uiState.value.title.isEmpty()) {
            SnackbarManager.showMessage(R.string.review_title_empty)
            return
        }

        if (_uiState.value.review.isEmpty()) {
            SnackbarManager.showMessage(R.string.review_empty)
            return
        }

        _uiState.value = _uiState.value.copy(
            isPositive = uiState.value.rating > 3,
        )

        launchCatching {
            reviewService.create(
                Review(
                    reviewedBusinessId = _uiState.value.reviewedBusinessId,
                    reviewerUserId = _uiState.value.reviewerUserId,
                    title = _uiState.value.title,
                    rating = _uiState.value.rating,
                    review = _uiState.value.review,
                    postive = _uiState.value.isPositive,
                    date = _uiState.value.date,
                    name = _uiState.value.name,
                    userImageUrl = _uiState.value.profileUrl
                )
            )
        }.invokeOnCompletion {
            SnackbarManager.showMessage(R.string.review_added)
        }
    }
}