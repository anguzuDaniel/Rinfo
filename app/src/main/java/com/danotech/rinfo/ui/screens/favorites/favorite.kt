package com.danotech.rinfo.ui.screens.favorites

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.Review
import com.danotech.rinfo.ui.components.ReviewCard
import com.danotech.rinfo.ui.components.RinfoBottomNavigation
import com.danotech.rinfo.ui.screens.RInfoScreen
import com.danotech.rinfo.ui.screens.appbars.CenteredBottomBarLayout
import com.danotech.rinfo.ui.screens.appbars.RinfoTopAppBar
import com.danotech.rinfo.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    onBackPressed: () -> Unit = {},
    onFabClicked: () -> Unit = {},
    onReviewCardClicked: (Review) -> Unit = {},
    onTabSelected: (RInfoScreen) -> Unit = {},
) {
    BackHandler {
        onBackPressed()
    }

    val reviews = listOf(
        Review(
            id = 1,
            avatarResource = R.drawable.baseline_person_24,
            imageUrl = R.drawable.cafe_javas,
            businessName = "Cafe Javas",
            rating = 4,
            comment = "This place has the best coffee and sandwiches in town!"
        ),
        Review(
            id = 2,
            avatarResource = R.drawable.baseline_person_24,
            imageUrl = R.drawable.kfc,
            businessName = "KFC",
            rating = 3,
            comment = "This kitchen place in town!"
        )
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            RinfoTopAppBar(
                title = stringResource(id = R.string.favorites),
                isShowingHomePage = false,
                onBackButtonClicked = onBackPressed,
            )
        },
        bottomBar = {
            CenteredBottomBarLayout(bottomBar = {
                RinfoBottomNavigation(
                    currentScreen = RInfoScreen.Favourites,
                    onTabSelected = onTabSelected,
                    modifier = Modifier.fillMaxWidth()
                )
            }, fab = {
                FloatingActionButton(
                    onClick = onFabClicked,
                    modifier = Modifier.padding(bottom = 10.dp),
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(id = R.string.favorites)
                    )
                }
            })
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.body_padding))
                .fillMaxSize(),
            contentPadding = innerPadding
        ) {
            items(reviews, key = { review -> review.id }) { review ->
                ReviewCard(
                    review = review,
                    onReviewCardClicked = onReviewCardClicked
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.body_padding)))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteScreenPreview() {
    AppTheme {
        FavoriteScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteScreenDarkPreview() {
    AppTheme(
        darkTheme = true
    ) {
        FavoriteScreen()
    }
}