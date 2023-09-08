package com.danotech.rinfo.ui.screens.business.subsections

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.model.Business
import com.danotech.rinfo.model.Review
import com.danotech.rinfo.ui.screens.review.ReviewItem
import com.danotech.rinfo.ui.screens.review.ReviewScreenViewModel
import com.danotech.rinfo.ui.screens.review.ReviewStatisticsScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BusinessReviewSection(
    modifier: Modifier = Modifier,
    business: Business,
    reviewScreenViewModel: ReviewScreenViewModel,
    onEditClicked: (String) -> Unit = {},
    onBackButtonClick: () -> Unit = {},
    onReportClicked: () -> Unit = {},
    onReplyClicked: () -> Unit = {},
    onLikeClicked: () -> Unit = {},
    onShareClicked: () -> Unit = {},
    onAllReview: () -> Unit = {}
) {
    val spaceLarge = 20.dp
    val spaceMedium = 10.dp

    val reviewUiState = reviewScreenViewModel.uiState.value

    val reviews = reviewUiState.reviews

    LaunchedEffect(reviewScreenViewModel) {
        reviewScreenViewModel.getReviewByBusinessId(business.id)
    }

    val openDialog = remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.body_padding))
            .fillMaxWidth(),
    ) {
        ReviewStatisticsScreen(
            count = 45
        )
        Spacer(modifier = Modifier.height(spaceLarge))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(spaceLarge))

        if (reviewUiState.reviews.isEmpty()) {
            Text(
                text = stringResource(R.string.no_reviews_added_yet),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(spaceMedium)
            )
        } else {
            reviews.forEach { review: Review ->
                ReviewItem(
                    viewModel = reviewScreenViewModel,
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
                                    reviewScreenViewModel.deleteReview(review.id, business.id)
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

            Column(
                modifier = Modifier
                    .padding(
                        vertical = 8.dp,
                        horizontal = dimensionResource(id = R.dimen.body_padding)
                    )
                    .fillMaxWidth(),
            ) {
                OutlinedButton(
                    onClick = onAllReview,
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(
                        1.dp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.see_reviews),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}