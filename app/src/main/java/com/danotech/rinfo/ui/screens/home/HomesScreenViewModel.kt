package com.danotech.rinfo.ui.screens.home

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.danotech.rinfo.RinfoViewModel
import com.danotech.rinfo.model.Business
import com.danotech.rinfo.model.Review
import com.danotech.rinfo.model.service.AccountService
import com.danotech.rinfo.model.service.BusinessAccountService
import com.danotech.rinfo.model.service.LogService
import com.danotech.rinfo.model.service.ReviewService
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

/***
 *
 *
 */
@HiltViewModel
class HomesScreenViewModel @Inject constructor(
    private val businessAccountService: BusinessAccountService,
    private val reviewService: ReviewService,
    private val accountService: AccountService,
    logService: LogService,
) : RinfoViewModel(logService) {
    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState = _uiState.asStateFlow()

    val businessFlow: Flow<List<Business>> = flow {
        _uiState.value = _uiState.value.copy(isLoading = true)

        val businesses = businessAccountService.getAllBusiness(5).first()

        emit(businesses)
    }.onStart {
        _uiState.value = _uiState.value.copy(isLoading = true)
    }.onCompletion {
        _uiState.value = _uiState.value.copy(isLoading = false)
    }

    init {
        initializeUIState()
    }

    private fun initializeUIState() {
        _uiState.value = HomeScreenUiState()
    }

    fun showReviews(): List<Review> {
        return _uiState.value.reviews
    }

    fun isLoggedIn(): Boolean {
        return true
    }

    fun getBusinesses() {
        _uiState.value = _uiState.value.copy(isLoading = true)

        launchCatching {
            val businesses = businessAccountService.getAllBusiness(5).first()

        }.invokeOnCompletion {
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }
}