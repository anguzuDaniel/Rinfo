package com.danotech.rinfo.ui.screens.review

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.FilterRow

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReviewContent(
    viewModel: ReviewScreenViewModel,
    businessId: String,
    innerPadding: PaddingValues = PaddingValues(0.dp),
    onEditClicked: (String) -> Unit
) {
    val reviewUiState = viewModel.uiState.collectAsState().value
    val openDialog = remember { mutableStateOf(false) }
    // Define a mutable state variable to store the selected review ID to delete
    var selectedReviewIdToDelete by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentPadding = innerPadding
    ) {
        item {
            FilterRow(
                heading = stringResource(id = R.string.reviews),
                paddingHorizontal = R.dimen.empty_padding
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
                    onEditClicked = { onEditClicked(review.id) },
                    onDeleteClicked = {
                        selectedReviewIdToDelete = review.id
                    },
                )
            }

            item {
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
                                    viewModel.deleteReview(
                                        selectedReviewIdToDelete!!,
                                        businessId
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
}