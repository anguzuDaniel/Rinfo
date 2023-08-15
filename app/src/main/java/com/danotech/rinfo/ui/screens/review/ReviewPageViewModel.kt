package com.danotech.rinfo.ui.screens.review

import androidx.compose.runtime.mutableStateOf
import com.danotech.rinfo.model.Business
import com.danotech.rinfo.model.service.BusinessAccountService
import com.danotech.rinfo.model.service.LogService
import com.danotech.rinfo.model.service.ReviewService
import com.danotech.rinfo.ui.screens.RinfoViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class ReviewPageViewModel @Inject constructor(
    private val businessAccountService: BusinessAccountService,
    logService: LogService
) : RinfoViewModel(logService) {
    val uiState = mutableStateOf(ReviewPageUiState())

    // Calculate total reviews
    val totalReviews: Int
        get() = uiState.value.currentBusiness.reviews

    // Calculate positive reviews
    val positiveReviews: Int
        get() = uiState.value.currentReview.count { it.postive }

    private val currentBusinessId: String
        get() = uiState.value.currentBusiness.id

    init {
        initializeUIState()
    }

    private fun initializeUIState() {
        uiState.value = ReviewPageUiState()
    }

    fun onReviewPageStart() {
        uiState.value = uiState.value.copy(
            showReviewPage = true
        )
    }

    fun getReview() {
        uiState.value = uiState.value.copy(
            currentReview = emptyList(),
            showReviewPage = true
        )
    }

    fun onReviewPageInput(review: String) {
        uiState.value = uiState.value.copy(
            reviewInput = review
        )
    }

    fun onSearch(search: String) {
        uiState.value = uiState.value.copy(
            isLoading = true
        )
    }

    /**
     * Gets a business by id
     * @param businessId The id of the business to get
     * @return A flow of the business
     * @throws Exception If the business could not be found
     */
    fun getBusinessById(businessId: String): Flow<Business?> {
        return flow {
            // Set isLoading to true
            uiState.value = uiState.value.copy(isLoading = true)

            val business = businessAccountService.getBusinessById(businessId)
            emit(business)
        }.onStart {
            // Set isLoading to true when the flow starts
            uiState.value = uiState.value.copy(isLoading = true)
        }.onCompletion {
            // Set isLoading to false when the flow completes (regardless of success or error)
            uiState.value = uiState.value.copy(isLoading = false)
        }
    }


}