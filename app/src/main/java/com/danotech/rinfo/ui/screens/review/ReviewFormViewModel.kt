package com.danotech.rinfo.ui.screens.review

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import com.danotech.rinfo.R
import com.danotech.rinfo.common.snackbar.SnackbarManager
import com.danotech.rinfo.model.Review
import com.danotech.rinfo.model.service.LogService
import com.danotech.rinfo.model.service.ReviewService
import com.danotech.rinfo.ui.screens.RinfoViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ReviewFormViewModel @Inject constructor(
    private val reviewService: ReviewService,
    logService: LogService
) : RinfoViewModel(logService) {
    private val _uiState = MutableStateFlow(ReviewFormUiState())
    val uiState = _uiState.asStateFlow()

    // Function to reset state when entering edit mode
    fun resetStateForEdit() {
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
        launchCatching {
            _uiState.value = _uiState.value.copy(
                isLoading = true
            )

            val review = reviewService.getReviewById(reviewId)

            _uiState.value = _uiState.value.copy(
                title = review.title,
                review = review.review,
                rating = review.rating,
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
                    date = _uiState.value.date
                )
            )
        }.invokeOnCompletion {
            SnackbarManager.showMessage(R.string.review_added)
        }
    }
}