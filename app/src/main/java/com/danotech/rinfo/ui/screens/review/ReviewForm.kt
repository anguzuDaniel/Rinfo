package com.danotech.rinfo.ui.screens.review

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.ReviewInputWithLabel
import com.danotech.rinfo.ui.screens.business.bottomSheet.ButtonRow
import com.danotech.rinfo.ui.screens.business.bottomSheet.RatingInputRow
import com.google.firebase.auth.FirebaseAuth

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun ReviewForm(
    reviewedBusinessId: String,
    reviewerUserId: String,
    viewModel: ReviewFormViewModel = hiltViewModel(),
    onCancel: () -> Unit,
    onSubmit: () -> Unit = {},
    onBackPressed: () -> Unit
) {
    val uiState = viewModel.uiState.value

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text(
            text = "Add a review",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))

        RatingInputRow(
            rating = uiState.rating,
            onRatingChange = viewModel::onRatingChanged
        )

        Spacer(modifier = Modifier.height(16.dp))

        ReviewInputWithLabel(
            placeholder = R.string.title,
            value = uiState.title,
            onValueChanged = viewModel::onTitleInput,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        ReviewInputWithLabel(
            placeholder = R.string.review,
            value = uiState.review,
            onValueChanged = viewModel::onReviewInput,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        ButtonRow(
            onCancel = onCancel,
            onSubmit = {
                // add user id before calling the add review method
                viewModel.addBusinessIdInformation(
                    reviewedBusinessId = reviewedBusinessId,
                    reviewerUserId = FirebaseAuth.getInstance().currentUser!!.email.toString()
                )
                viewModel.addReview()
                onCancel()
            },
            submitButtonEnabled = uiState.review.isNotEmpty() && uiState.title.isNotEmpty(),
        )
    }
}