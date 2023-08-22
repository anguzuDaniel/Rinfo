package com.danotech.rinfo.ui.screens.business

import androidx.lifecycle.viewModelScope
import com.danotech.rinfo.model.Business
import com.danotech.rinfo.model.service.BusinessAccountService
import com.danotech.rinfo.model.service.LogService
import com.danotech.rinfo.ui.screens.RinfoViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusinessViewModel
@Inject
constructor(
    private val businessAccountService: BusinessAccountService,
    logService: LogService
) : RinfoViewModel(logService) {
    private val _uiState =
        MutableStateFlow(BusinessUiState(isLoading = false, currentBusinessId = Business().id))
    val uiState = _uiState.asStateFlow()

    // Calculate total reviews
    val totalReviews: Int
        get() = _uiState.value.currentBusiness.reviews

    // Calculate positive reviews
    val positiveReviews: Int
        get() = _uiState.value.currentReview.count { it.postive }

    private val currentBusinessId: String
        get() = _uiState.value.currentBusiness.id

    fun onReviewPageStart() {
        _uiState.value = _uiState.value.copy(
            showReviewPage = true
        )
    }

    fun getReview() {
        _uiState.value = _uiState.value.copy(
            currentReview = emptyList(),
            showReviewPage = true
        )
    }

    fun getCurrentBusiness(currentBusinessId: String) {
        _uiState.value = _uiState.value.copy(currentBusinessId = currentBusinessId)
    }

    fun onReviewPageInput(review: String) {
        _uiState.value = _uiState.value.copy(
            reviewInput = review
        )
    }

    fun onSearch(search: String) {
        _uiState.value = _uiState.value.copy(
            isLoading = true
        )
    }

    /**
     * Gets a business by id
     * @param businessId The id of the business to get
     * @return A flow of the business
     * @throws Exception If the business could not be found
     */
    fun getBusinessById(businessId: String) {
        launchCatching {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                val business =
                    businessAccountService.getBusinessById(businessId = businessId)
                // Update other relevant state variables like business details
                _uiState.value = _uiState.value.copy(currentBusiness = business!!)
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