package com.danotech.rinfo.ui.screens.review

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.model.Business
import com.danotech.rinfo.ui.components.BusinessBottomAppBar
import com.danotech.rinfo.ui.components.RatingStars
import com.danotech.rinfo.ui.screens.appbars.RinfoTopAppBar
import com.danotech.rinfo.ui.screens.review.bottomSheet.ReviewInputBottomSheet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ReviewScreen(
    businessId: String,
    reviewerUserId: String,
    viewModel: ReviewPageViewModel = hiltViewModel(),
    onBackPressed: () -> Unit = {},
    onSearchIconClicked: () -> Unit = {},
) {
    BackHandler {
        onBackPressed()
    }

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false,
        )
    )

    val coroutineScope = rememberCoroutineScope()

    val businessState: State<Business?> =
        viewModel.getBusinessById(businessId = businessId).collectAsState(
            initial = null
        )

    Log.d("ReviewScreen", "businessState: $businessState")
    Log.d("ReviewScreen", "businessId: $businessId")

    ReviewInputBottomSheet(
        bottomSheetScaffoldState = bottomSheetScaffoldState,
        onCancel = {
            coroutineScope.launch {
                bottomSheetScaffoldState.bottomSheetState.hide()
            }
        },
        onSubmit = {},
        reviewedBusinessId = businessId,
        reviewerUserId = ""
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = if (viewModel.uiState.value.isLoading) {
                {}
            } else {
                {
                    RinfoTopAppBar(
                        isShowingHomePage = false,
                        showBackgroundColor = false,
                        onBackButtonClicked = onBackPressed,
                        actions = {
                            IconButton(
                                onClick = onSearchIconClicked,
                                modifier = Modifier
                                    .padding(1.dp)
                                    .background(
                                        MaterialTheme.colorScheme.surface,
                                        shape = CircleShape
                                    ),
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = stringResource(R.string.bookmark_business),
                                    tint = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                    )
                }
            },
            bottomBar = if (viewModel.uiState.value.isLoading) {
                {}
            } else {
                {
                    BusinessBottomAppBar(
                        onClick = {
                            coroutineScope.launch {
                                bottomSheetScaffoldState.bottomSheetState.expand()
                            }
                        }
                    )
                }
            }
        ) {
            if (viewModel.uiState.value.isLoading) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .animateContentSize(
                            animationSpec = (tween(
                                durationMillis = 300,
                                easing = LinearOutSlowInEasing
                            ))
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            } else {
                ReviewContent(
                    business = businessState.value ?: Business(),
                )
            }
        }
    }
}

@Composable
fun collectBusinessState(businessFlow: Flow<Business?>): State<Business?> {
    // Collect the flow and convert it into a Compose State
    return businessFlow.collectAsState(initial = null)
}

@Composable
fun ReviewContent(
    viewModel: ReviewPageViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    business: Business = Business()
) {
    Column {
        Image(
            painter = painterResource(id = R.drawable.cafe_javas),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop
        )

        LazyColumn(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.body_padding))
        ) {
            item {
                Text(
                    text = business.name,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.padding(5.dp))
            }

            item {
                RatingRow(business = business)
            }

            item {
                Text(
                    text = business.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            item {
                val totalReviews = 50
                val positiveReviews = 10
                val negativeReviews = totalReviews - positiveReviews
                val count = business.reviews

                Spacer(modifier = Modifier.padding(5.dp))

                ReviewStatistics(
                    count = count,
                    totalReviews = totalReviews,
                    positiveReviews = positiveReviews,
                    negativeReviews = negativeReviews
                )
            }
        }
    }
}

@Composable
fun ReviewStatistics(
    count: Int,
    totalReviews: Int,
    positiveReviews: Int,
    negativeReviews: Int,
    modifier: Modifier = Modifier
) {
    Card {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Review Statistics",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "$count.0",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Total Reviews: $totalReviews",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(10.dp))

            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                progress = positiveReviews.toFloat() / totalReviews.toFloat(),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Positive Reviews: $positiveReviews (${(positiveReviews.toFloat() / totalReviews.toFloat() * 100).toInt()}%)",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Spacer(modifier = Modifier.height(10.dp))

            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                progress = negativeReviews.toFloat() / totalReviews.toFloat(),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Negative Reviews: $negativeReviews (${(negativeReviews.toFloat() / totalReviews.toFloat() * 100).toInt()}%)",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}


@Composable
fun RatingRow(
    business: Business
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RatingStars(rating = business.reviews)

        Icon(
            imageVector = Icons.Default.Bookmark,
            contentDescription = stringResource(id = R.string.favorites),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
    Spacer(modifier = Modifier.padding(5.dp))
}