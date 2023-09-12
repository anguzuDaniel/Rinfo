package com.danotech.rinfo.ui.screens.review

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danotech.rinfo.R
import com.danotech.rinfo.helpers.timeAgo
import com.danotech.rinfo.model.Review
import com.danotech.rinfo.ui.components.ProfileImageBitmap
import com.danotech.rinfo.ui.components.ProfileImageShimmer
import com.danotech.rinfo.ui.components.RatingStars
import com.danotech.rinfo.ui.screens.business.subsections.components.StarRating
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReviewItem(
    viewModel: ReviewScreenViewModel,
    review: Review,
    modifier: Modifier = Modifier,
    onEditClicked: () -> Unit = {},
    onDeleteClicked: () -> Unit = {},
    onReportClicked: () -> Unit = {},
    onReplyClicked: () -> Unit = {},
    onLikeClicked: () -> Unit = {},
    onShareClicked: () -> Unit = {},
) {
    val context = LocalContext.current
    val logoImage: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.no_image)

    val bitmap = remember {
        mutableStateOf(logoImage)
    }

    LaunchedEffect(viewModel) {
        viewModel.getUserNameById(review.reviewerUserId)
        viewModel.getProfileImage(userId = review.reviewerUserId, bitmap = bitmap)
    }

    val uiState = viewModel.uiState.collectAsState().value

    Column {
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = modifier
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val imageSize = 35.dp
            ProfileImageShimmer(
                size = imageSize,
                isLoading = uiState.imageLoading
            ) {
                ProfileImageBitmap(
                    size = 35.dp,
                    bitmap = bitmap.value
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = review.reviewerUserId,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(text = ".")

                    Spacer(modifier = Modifier.width(4.dp))

                    if (!review.edited) {
                        // if the review has a date
                        if (review.date.isNotEmpty()) {
                            // shows the time difference between the current time and the time the review was added
                            // to display eg 1 min ago
                            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                            // val storedDateTimeString = "2023-08-13 10:00:00" // Not needed
                            val storedDateTime = LocalDateTime.parse(review.date.trim(), formatter)
                            val currentDateTime = LocalDateTime.now()

                            val timeDifference = timeAgo(storedDateTime, currentDateTime)

                            Text(
                                text = timeDifference,
                                style = MaterialTheme.typography.bodySmall,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Light
                            )
                        }
                    } else {
                        Text(
                            text = stringResource(R.string.edited),
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Light,
                            fontStyle = FontStyle.Italic
                        )

                        Spacer(modifier = Modifier.width(2.dp))

                        // if the review has a date
                        if (review.date.isNotEmpty()) {
                            // shows the time difference between the current time and the time the review was added
                            // to display eg 1 min ago
                            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                            // val storedDateTimeString = "2023-08-13 10:00:00" // Not needed
                            val storedDateTime = LocalDateTime.parse(review.date.trim(), formatter)
                            val currentDateTime = LocalDateTime.now()

                            val timeDifference = timeAgo(storedDateTime, currentDateTime)

                            Text(
                                text = timeDifference,
                                style = MaterialTheme.typography.bodySmall,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Light,
                                fontStyle = FontStyle.Italic
                            )
                        }
                    }

                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        modifier = Modifier.weight(2f)
                    ) {
                        StarRating(rating = review.rating)

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = "${review.rating}.0",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            if (review.reviewerUserId == FirebaseAuth.getInstance().currentUser?.email) {
                CurrentUserReviewDropdownActionOptions(
                    onEditClicked = onEditClicked,
                    onDeleteClicked = onDeleteClicked
                )
            } else {
                ReviewDropdownActionOptions(
                    onReportClicked = onReportClicked,
                    onReplyClicked = onReplyClicked,
                    onLikeClicked = onLikeClicked,
                    onShareClicked = onShareClicked
                )
            }
        }

        Text(
            text = review.title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        ExpandableReviewText(review.review)
    }

    Spacer(modifier = Modifier.height(15.dp))
}

@Composable
fun ExpandableReviewText(review: String) {
    var isExpanded by remember { mutableStateOf(false) }

    val maxWords = 50
    val wordsToShow = if (isExpanded) {
        review.split(" ").joinToString(" ") // Show the entire review when expanded
    } else {
        review.split(" ").take(maxWords).joinToString(" ") // Show the first 50 words
    }

    Text(
        text = AnnotatedString(wordsToShow),
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.clickable { isExpanded = !isExpanded }
    )

    if (!isExpanded && review.split(" ").size > maxWords) {
        Text(
            text = AnnotatedString("... Read More"),
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline
            ),
            modifier = Modifier.clickable { isExpanded = true }
        )
    } else if (isExpanded) {
        Text(
            text = AnnotatedString(" Read Less"),
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline
            ),
            modifier = Modifier.clickable { isExpanded = false }
        )
    }
}
