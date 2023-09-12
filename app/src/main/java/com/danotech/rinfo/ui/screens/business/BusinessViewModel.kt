package com.danotech.rinfo.ui.screens.business

import android.graphics.Bitmap
import com.danotech.rinfo.model.Business
import com.danotech.rinfo.model.service.BusinessAccountService
import com.danotech.rinfo.model.service.LogService
import com.danotech.rinfo.ui.screens.RinfoViewModel
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.ByteArrayOutputStream
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

    fun addImages(images: List<Bitmap>) {
        _uiState.value = _uiState.value.copy(
            currentBusinessImages = images
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

    /**
     * add business Images
     *
     */
    fun addBusinessImages(
        businessId: String,
        imageList: List<Bitmap>,
        onComplete: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        launchCatching {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val storage = FirebaseStorage.getInstance()
            val storageRef = storage.reference

            val imagesLoadingCount = imageList.size

            imageList.forEachIndexed { index, bitmap ->
                val imageName = "${businessId}_${index}.jpg"
                val imageRef = storageRef.child("business_images/$imageName")

                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()

                val uploadTask = imageRef.putBytes(data)
                uploadTask.addOnSuccessListener {
                    if (index == imagesLoadingCount - 1) {
                        onComplete.invoke()
                    }
                }.addOnFailureListener { exception ->
                    onError.invoke(exception)
                }
            }
        }.invokeOnCompletion {
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }
}