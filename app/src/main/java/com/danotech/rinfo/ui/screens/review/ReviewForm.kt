package com.danotech.rinfo.ui.screens.review

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.ReviewInputWithLabel
import com.danotech.rinfo.ui.screens.business.bottomSheet.ButtonRow
import com.danotech.rinfo.ui.screens.business.bottomSheet.RatingInputRow
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun ReviewForm(
    reviewedBusinessId: String,
    reviewId: String,
    reviewerUserId: String,
    viewModel: ReviewFormViewModel = hiltViewModel(),
    onCancel: () -> Unit,
    onSubmit: () -> Unit = {},
    onBackPressed: () -> Unit
) {
    if (reviewId.isNotEmpty()) {
        LaunchedEffect(viewModel) {
            viewModel.getReview(reviewId)
        }
    }

    val uiState = viewModel.uiState.collectAsState().value

    Scaffold {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.add_a_review),
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
                onValueChanged = { title ->
                    // title is less than 10 characters
                    // return
                    if (title.length <= 10) {
                        viewModel.onTitleInput(title)
                    }

                }, modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            ReviewInputWithLabel(
                placeholder = R.string.review,
                value = uiState.review,
                onValueChanged = viewModel::onReviewInput,
                modifier = Modifier.fillMaxWidth()
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

                    // get the current data and time
                    // add the date and time before calling the add review method
                    val currentDateTime = LocalDateTime.now()
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    val formattedDateTime = currentDateTime.format(formatter)
                    viewModel.onDateInput(formattedDateTime)

                    // if the review id is not empty
                    // update the review
                    if (reviewId == "") {
                        // adds the review to the database
                        viewModel.addReview()
                    } else {
                        // updated the review from the database
                        viewModel.updateReview(reviewId)
                    }

                    onCancel()
                },
                submitButtonEnabled = uiState.review.isNotEmpty() && uiState.title.isNotEmpty(),
            )
        }
    }
}