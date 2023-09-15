@file:Suppress("KDocUnresolvedReference")

package com.danotech.rinfo.ui.screens.business

import android.annotation.SuppressLint
import android.os.Build
import android.view.Window
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.ui.screens.appbars.RinfoTopAppBar
import com.danotech.rinfo.ui.screens.business.components.BusinessActionDropDown
import com.danotech.rinfo.ui.screens.business.components.BusinessContent
import com.danotech.rinfo.ui.screens.business.components.BusinessScreenShimmer
import com.danotech.rinfo.ui.screens.review.ReviewScreenViewModel
import com.google.firebase.auth.FirebaseAuth

@RequiresApi(Build.VERSION_CODES.Q)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BusinessScreen(
    businessId: String,
    viewModel: BusinessViewModel = hiltViewModel(),
    reviewScreenViewModel: ReviewScreenViewModel = hiltViewModel(),
    onBackPressed: () -> Unit = {},
    onAddReviewClick: () -> Unit = {},
    onShowReviewPageClicked: () -> Unit = {},
    onShowBusinessPhotos: () -> Unit = {},
    window: Window,
    onDirectionClicked: (String) -> Unit = {},
) {
    BackHandler {
        onBackPressed()
    }

    LaunchedEffect(viewModel) {
        viewModel.getBusinessById(businessId)
        reviewScreenViewModel.getReviewByBusinessId(businessId = businessId)
    }

    val uiState by viewModel.uiState.collectAsState()
    val reviewUiState by reviewScreenViewModel.uiState.collectAsState()
    val reviews = reviewUiState.reviews

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = if (uiState.isLoading) {
            {}
        } else {
            {
                RinfoTopAppBar(
                    isShowingHomePage = false,
                    showBackgroundColor = false,
                    onBackButtonClicked = onBackPressed,
                    actions = {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // if the dropdown action is clicked
                            if (uiState.currentBusiness.userId == FirebaseAuth.getInstance().currentUser?.email) {
                                BusinessActionDropDown(
                                    onEditClicked = {},
                                    onDeleteClicked = {}
                                )
                            } else {
                                BusinessActionDropDown(
                                    onEditClicked = {},
                                    onDeleteClicked = {}
                                )
                            }
                        }
                    }
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { innerPadding ->
        BusinessScreenShimmer(
            isLoading = uiState.isLoading
        ) {
            BusinessContent(
                businessId = businessId,
                business = uiState.currentBusiness,
                onShowReviewPageClicked = onShowReviewPageClicked,
                onDirectionClicked = {
                    onDirectionClicked(it)
                },
                reviewScreenViewModel = reviewScreenViewModel,
                viewModel = viewModel,
                modifier = Modifier.consumeWindowInsets(innerPadding),
                window = window,
                onAddReviewButtonClick = onAddReviewClick,
                reviews = reviews,
                loading = uiState.imagesLoading,
                onShowBusinessPhotos = onShowBusinessPhotos
            )
        }
    }
}