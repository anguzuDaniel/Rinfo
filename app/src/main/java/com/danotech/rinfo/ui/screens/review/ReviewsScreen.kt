package com.danotech.rinfo.ui.screens.review

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.data.LocalReviewProvider
import com.danotech.rinfo.model.Review
import com.danotech.rinfo.model.local.Category
import com.danotech.rinfo.ui.components.ProfileImageBitmap
import com.danotech.rinfo.ui.components.ProfileImageShimmer
import com.danotech.rinfo.ui.components.RatingStars
import com.danotech.rinfo.ui.screens.appbars.RinfoTopAppBar
import com.danotech.rinfo.ui.screens.home.FilterRow
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

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
//                item {
//                    ReviewStatistics(
//                        count = reviewUiState.reviews.size,
//                        positiveReviews = 45,
//                        negativeReviews = 5,
//                        totalReviews = 50
//                    )
//                    Spacer(modifier = Modifier.height(20.dp))
//                    HorizontalDivider()
//                    Spacer(modifier = Modifier.height(20.dp))
//                }

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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReviewItem(
    viewModel: ReviewScreenViewModel,
    review: Review,
    modifier: Modifier = Modifier,
    onReviewItemClicked: (String) -> Unit = {},
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
                        RatingStars(rating = review.rating)

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

        Text(
            text = review.review,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Light
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectBusinessCategory(
    modifier: Modifier = Modifier,
    onAccountTypeSelected: (Category) -> Unit = {}
) {
    val listItems = LocalReviewProvider.categories

    var expanded by remember {
        mutableStateOf(false)
    }

    var selectedItem by remember {
        mutableStateOf(listItems[0])
    }

    val focusRequester = remember {
        FocusRequester()
    }

    Box(
        modifier = modifier
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
        ) {
            TextField(
                value = selectedItem.name,
                onValueChange = { onAccountTypeSelected(selectedItem) },
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded,
                    )
                },
                modifier = Modifier
                    .menuAnchor(),
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
            )

            // menu
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .menuAnchor()
            ) {
                // this is a column scope
                // all the items are added vertically
                listItems.forEach { selectedOption ->
                    // menu item
                    DropdownMenuItem(
                        text = { Text(text = selectedOption.name) },
                        onClick = {
                            selectedItem = selectedOption
                            onAccountTypeSelected(selectedOption)
                            expanded = false
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun ReviewDropdownActionOptions(
    onReportClicked: () -> Unit = {},
    onReplyClicked: () -> Unit = {},
    onLikeClicked: () -> Unit = {},
    onShareClicked: () -> Unit = {},
) {
    LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Share") },
                onClick = onShareClicked
            )
            DropdownMenuItem(
                text = { Text("Like") },
                onClick = onLikeClicked
            )
            DropdownMenuItem(
                text = { Text("Reply") },
                onClick = onReplyClicked
            )
            DropdownMenuItem(
                text = { Text("Report") },
                onClick = onReportClicked
            )
        }
    }
}

@Composable
fun CurrentUserReviewDropdownActionOptions(
    onEditClicked: () -> Unit = {},
    onDeleteClicked: () -> Unit = {}
) {
    LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Edit") },
                onClick = onEditClicked
            )
            DropdownMenuItem(
                text = { Text("Delete") },
                onClick = {
                    onDeleteClicked()
                    expanded = false
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun timeAgo(from: LocalDateTime, to: LocalDateTime = LocalDateTime.now()): String {
    val duration = ChronoUnit.SECONDS.between(from, to)

    return when {
        duration < 60 -> "$duration seconds ago"
        duration < 3600 -> "${duration / 60} minutes ago"
        duration < 86400 -> "${duration / 3600} hours ago"
        duration == 86400L -> "1 day ago"
        else -> "${duration / 86400} days ago"
    }
}