package com.danotech.rinfo.ui.screens.business

import android.annotation.SuppressLint
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.model.Business
import com.danotech.rinfo.ui.components.RatingStars
import com.danotech.rinfo.ui.components.RinfoButton
import com.danotech.rinfo.ui.components.RinfoFAB
import com.danotech.rinfo.ui.components.TruncateText
import com.danotech.rinfo.ui.screens.appbars.RinfoTopAppBar
import kotlinx.coroutines.flow.Flow

@RequiresApi(Build.VERSION_CODES.Q)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BusinessScreen(
    businessId: String,
    reviewerUserId: String,
    viewModel: BusinessViewModel = hiltViewModel(),
    onBackPressed: () -> Unit = {},
    onSearchIconClicked: () -> Unit = {},
    onFabBtnClicked: () -> Unit = {},
    onShowReviewPageClicked: () -> Unit = {},
) {
    BackHandler {
        onBackPressed()
    }

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.getBusinessById(businessId = businessId)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = if (uiState.isLoading) {
            {}
        } else {
            {
                RinfoTopAppBar(
                    isShowingHomePage = false,
                    showBackgroundColor = false,
                    onBackButtonClicked = onBackPressed,
                    actions = {
                        IconButton(
                            onClick = onSearchIconClicked,
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.surface,
                                    shape = CircleShape
                                ),
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = stringResource(R.string.bookmark_business),
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                )
            }
        },
        floatingActionButton = if (uiState.isLoading) {
            {}
        } else {
            { RinfoFAB(onClick = onFabBtnClicked) }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) {
        if (!uiState.isLoading) {
            BusinessContent(
                business = uiState.currentBusiness,
                onShowReviewPageClicked = onShowReviewPageClicked
            )
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
fun collectBusinessState(businessFlow: Flow<Business?>): State<Business?> {
    // Collect the flow and convert it into a Compose State
    return businessFlow.collectAsState(initial = null)
}

@Composable
fun BusinessContent(
    modifier: Modifier = Modifier,
    business: Business,
    onShowReviewPageClicked: () -> Unit = {},
) {
    var isShowingAllDescriptionText by remember {
        mutableStateOf(false)
    }

    val clickableText = if (isShowingAllDescriptionText) "less" else "See more"

    Column {
        Image(
            painter = painterResource(id = R.drawable.cafe_javas),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop
        )

        LazyColumn(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.body_padding))
        ) {
            item {
                Text(
                    text = business.name,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.padding(5.dp))
            }

            item {
                RatingRow(reviews = business.reviews)
            }

            item {
                RinfoButton(
                    name = R.string.call,
                    onClicked = { /*TODO*/ },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Spacer(modifier = Modifier.height(10.dp))
                Divider()
                Spacer(modifier = Modifier.height(10.dp))
            }

            item {
                Text(
                    text = stringResource(id = R.string.about),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(10.dp))
            }

            item {
                if (!isShowingAllDescriptionText) {
                    TruncateText(
                        text = business.description,
                        maxWords = 20,  // Set the desired maximum number of words
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                } else {
                    Text(
                        text = business.description,
                        style = TextStyle(
                            fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,

                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                ClickableText(
                    text = AnnotatedString(clickableText),
                    style = MaterialTheme.typography.bodyMedium.copy(color = if (isShowingAllDescriptionText) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary),
                    onClick = { isShowingAllDescriptionText = !isShowingAllDescriptionText }
                )
            }

            item {
                Spacer(modifier = Modifier.height(10.dp))
                Divider()
                Spacer(modifier = Modifier.height(10.dp))
            }

            item {
                OutlinedButton(
                    onClick = onShowReviewPageClicked,
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


@Composable
fun RatingRow(
    reviews: Int = 0
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RatingStars(rating = reviews)

        Icon(
            imageVector = Icons.Default.Bookmark,
            contentDescription = stringResource(id = R.string.favorites),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
    Spacer(modifier = Modifier.padding(5.dp))
}