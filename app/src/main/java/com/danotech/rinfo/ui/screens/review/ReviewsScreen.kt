package com.danotech.rinfo.ui.screens.review

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.screens.appbars.RinfoTopAppBar
import com.danotech.rinfo.ui.screens.home.FilterRow

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReviewsScreen(
    viewModel: ReviewScreenViewModel = hiltViewModel(),
    businessId: String,
    userId: String,
    onBackButtonClick: () -> Unit = {},
    onEditClicked: (String) -> Unit = {},
    onReportClicked: () -> Unit = {},
    onReplyClicked: () -> Unit = {},
    onLikeClicked: () -> Unit = {},
    onShareClicked: () -> Unit = {},
) {
    val reviewUiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(viewModel) {
        viewModel.getReviewByBusinessId(businessId)
    }

    val openDialog = remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = if (!reviewUiState.isLoading) {
            {
                RinfoTopAppBar(
                    isShowingHomePage = false,
                    title = stringResource(id = R.string.reviews),
                    onBackButtonClicked = onBackButtonClick
                )
            }
        } else {
            {}
        },
    ) {
        if (!reviewUiState.isLoading) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentPadding = it
            ) {
                item {
                    FilterRow(
                        heading = stringResource(id = R.string.reviews)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

                if (reviewUiState.reviews.isEmpty()) {
                    item {
                        Text(
                            text = stringResource(R.string.no_reviews_added_yet),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                } else {
                    items(reviewUiState.reviews, key = { r -> r.id }) { review ->
                        ReviewItem(
                            viewModel = viewModel,
                            review = review,
                            onReviewItemClicked = {},
                            onEditClicked = { onEditClicked(review.id) },
                            onDeleteClicked = {
                                openDialog.value = true
                            },
                        )

                        if (openDialog.value) {
                            AlertDialog(
                                onDismissRequest = {
                                    // Dismiss the dialog when the user clicks outside the dialog or on the back
                                    // button. If you want to disable that functionality, simply use an empty
                                    // onDismissRequest.
                                    openDialog.value = false
                                },
                                title = {
                                    Text(text = stringResource(R.string.delete_review))
                                },
                                text = {
                                    Text(text = stringResource(R.string.are_you_sure_you_want_to_delete_this_review))
                                },
                                confirmButton = {
                                    TextButton(
                                        onClick = {
                                            viewModel.deleteReview(review.id, businessId)
                                            openDialog.value = false
                                        }
                                    ) {
                                        Text("Delete")
                                    }
                                },
                                dismissButton = {
                                    TextButton(
                                        onClick = {
                                            openDialog.value = false
                                        }
                                    ) {
                                        Text("Cancel")
                                    }
                                }
                            )
                        }
                    }
                }
            }
        } else {
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
        }
    }
}