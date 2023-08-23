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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.data.LocalReviewProvider
import com.danotech.rinfo.model.Review
import com.danotech.rinfo.model.local.Category
import com.danotech.rinfo.ui.components.ProfileImage
import com.danotech.rinfo.ui.components.RatingStars
import com.danotech.rinfo.ui.screens.appbars.RinfoTopAppBar

@Composable
fun ReviewsScreen(
    viewModel: ReviewScreenViewModel = hiltViewModel(),
    businessId: String,
    userId: String,
    onBackButtonClick: () -> Unit = {}
) {
    val reviewUiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(viewModel) {
        viewModel.getReviewByBusinessId(businessId)
    }

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
                        count = 50,
                        positiveReviews = 45,
                        negativeReviews = 5,
                        totalReviews = 50
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }

//            item {
//                SelectBusinessCategory()
//            }

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
                            review = review,
                            onReviewItemClicked = {}
                        )
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
    review: Review,
    modifier: Modifier = Modifier,
    onReviewItemClicked: (String) -> Unit = {}
) {
    Column(
        modifier = modifier.padding(10.dp)
    ) {
        Row(
            modifier = modifier
                .padding(bottom = 8.dp)
        ) {
            ProfileImage(
                size = 50.dp,
                imageUrI = R.drawable.cafe_javas
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = review.reviewerUserId,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(2f)
                    )

                    Text(
                        text = "20 Apr 2018",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                RatingStars(rating = review.rating)

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = review.review,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
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
