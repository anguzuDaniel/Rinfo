package com.danotech.rinfo.ui.screens.review

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.screens.appbars.RinfoTopAppBar
import com.danotech.rinfo.ui.screens.business.subsections.ReviewShimmer

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReviewsScreen(
    viewModel: ReviewScreenViewModel = hiltViewModel(),
    businessId: String,
    onBackButtonClick: () -> Unit = {},
    onEditClicked: (String) -> Unit = {},
) {
    val reviewUiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(viewModel) {
        viewModel.getReviewByBusinessId(businessId)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            RinfoTopAppBar(
                isShowingHomePage = false,
                title = stringResource(id = R.string.reviews),
                onBackButtonClicked = onBackButtonClick
            )
        }
    ) {
        ReviewShimmer(
            times = 10,
            isLoading = reviewUiState.isLoading,
            innerPadding = it
        ) {
            ReviewContent(
                viewModel = viewModel,
                innerPadding = it,
                businessId = businessId,
                onEditClicked = onEditClicked
            )
        }
    }
}