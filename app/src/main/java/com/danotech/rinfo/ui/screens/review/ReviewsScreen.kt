package com.danotech.rinfo.ui.screens.review

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.data.LocalReviewProvider
import com.danotech.rinfo.model.Review
import com.danotech.rinfo.model.local.Category
import com.danotech.rinfo.ui.components.ProfileImage
import com.danotech.rinfo.ui.components.RatingStars
import com.danotech.rinfo.ui.screens.appbars.RinfoTopAppBar
import com.danotech.rinfo.ui.screens.home.FilterRow
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterial3Api::class)
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
                    ReviewStatistics(
                        count = reviewUiState.reviews.size,
                        positiveReviews = 45,
                        negativeReviews = 5,
                        totalReviews = 50
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(20.dp))
                }

//            item {
//                SelectBusinessCategory()
//            }
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
                    items(reviewUiState.reviews, key = { review -> review.id }) { review ->
                        ReviewItem(
                            viewModel = viewModel,
                            review = review,
                            onReviewItemClicked = {},
                            onEditClicked = { onEditClicked(review.id) },
                            onDeleteClicked = {
                                openDialog.value = true
                            }
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
                                    Text(text = "Delete Review")
                                },
                                text = {
                                    Text(text = "Are you sure you want to delete this review?")
                                },
                                confirmButton = {
                                    TextButton(
                                        onClick = {
                                            viewModel.deleteReview(review.id)
                                            openDialog.value = false
                                        }
                                    ) {
                                        Text("Confirm")
                                    }
                                },
                                dismissButton = {
                                    TextButton(
                                        onClick = {
                                            openDialog.value = false
                                        }
                                    ) {
                                        Text("Dismiss")
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

    val reviewUiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(viewModel) {
        viewModel.getUserNameById(review.reviewerUserId)
    }

    Column {
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = modifier
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileImage(
                size = 35.dp,
                imageUrI = R.drawable.cafe_javas
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = reviewUiState.reviewUserName,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(text = ".")

                    Spacer(modifier = Modifier.width(4.dp))

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
                            text = timeDifference ?: "",
                            style = MaterialTheme.typography.bodyMedium
                        )
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
                            text = "${review.rating.toString()}.0",
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
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = review.review,
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
    Divider()
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
    val context = LocalContext.current
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
    val context = LocalContext.current
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
                onClick = onDeleteClicked
            )
        }
    }
}

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
