package com.danotech.rinfo.ui.screens.business.subsections

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Reviews
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.model.Business
import com.danotech.rinfo.model.Review
import com.danotech.rinfo.ui.screens.review.ReviewItem
import com.danotech.rinfo.ui.screens.review.ReviewScreenViewModel
import com.danotech.rinfo.ui.screens.review.ReviewStatisticsScreen
import com.danotech.rinfo.ui.theme.AppTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BusinessReviewSection(
    modifier: Modifier = Modifier,
    business: Business,
    reviewScreenViewModel: ReviewScreenViewModel,
    onEditClicked: (String) -> Unit = {},
    onAllButtonReviewClick: () -> Unit = {},
    onAddReviewButtonClick: () -> Unit = {},
    reviews: List<Review>
) {
    val spaceLarge = 20.dp
    val spaceMedium = 10.dp
    val reviewUiState = reviewScreenViewModel.uiState.value
    // Define a mutable state variable to store the selected review ID to delete
    var selectedReviewIdToDelete by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.body_padding))
            .fillMaxWidth(),
    ) {
        ReviewStatisticsScreen(
            count = 45
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedButton(
                onClick = onAddReviewButtonClick,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Reviews,
                        contentDescription = "review icon",
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = "Write a review",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(spaceLarge))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(spaceLarge))

        AnimatedVisibility(reviewUiState.hasMessage) {
            Text(
                text = reviewUiState.errorMessage,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(spaceMedium)
            )
        }

        ReviewShimmer(
            isLoading = reviewUiState.isLoading
        ) {
            if (reviewUiState.reviews.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_reviews_added_yet),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(spaceMedium)
                )
            } else {
                reviews.take(2).forEach { review: Review ->
                    ReviewItem(
                        viewModel = reviewScreenViewModel,
                        review = review,
                        onEditClicked = { onEditClicked(review.id) },
                        onDeleteClicked = {
                            // Set the selectedReviewIdToDelete when the delete button is clicked
                            selectedReviewIdToDelete = review.id
                        },
                    )
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Show all review",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable(onClick = onAllButtonReviewClick)
                    )
                }
            }

            // Create the AlertDialog outside the loop and show it conditionally
            if (selectedReviewIdToDelete != null) {
                AlertDialog(
                    onDismissRequest = {
                        // Dismiss the dialog when the user clicks outside the dialog or on the back button.
                        selectedReviewIdToDelete = null
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
                                reviewScreenViewModel.deleteReview(
                                    selectedReviewIdToDelete!!,
                                    business.id
                                )
                                selectedReviewIdToDelete = null
                            }
                        ) {
                            Text("Delete")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                selectedReviewIdToDelete = null
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

@Preview(showBackground = true)
@Composable
fun BusinessReviewSectionPreview() {
    AppTheme {
        OutlinedButton(
            onClick = {},
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Reviews,
                    contentDescription = "review icon",
                    tint = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "Write a review",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}