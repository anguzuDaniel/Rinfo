package com.danotech.rinfo.ui.screens.favorites

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.danotech.rinfo.R
import com.danotech.rinfo.model.Review
import com.danotech.rinfo.ui.components.RinfoBottomNavigation
import com.danotech.rinfo.ui.screens.RInfoScreen
import com.danotech.rinfo.ui.screens.appbars.CenteredBottomBarLayout
import com.danotech.rinfo.ui.screens.appbars.RinfoTopAppBar
import com.danotech.rinfo.ui.theme.AppTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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

//    val reviews = listOf(
//        Review(
//            id = 1,
//            avatarResource = R.drawable.baseline_person_24,
//            imageUrl = R.drawable.cafe_javas,
//            businessName = "Cafe Javas",
//            rating = 4,
//            comment = "This place has the best coffee and sandwiches in town!"
//        ),
//        Review(
//            id = 2,
//            avatarResource = R.drawable.baseline_person_24,
//            imageUrl = R.drawable.kfc,
//            businessName = "KFC",
//            rating = 3,
//            comment = "This kitchen place in town!"
//        )
//    )

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
//                FloatingActionButton(
//                    onClick = onFabClicked,
//                    modifier = Modifier.padding(bottom = 10.dp),
//                    contentColor = MaterialTheme.colorScheme.onPrimary,
//                    containerColor = MaterialTheme.colorScheme.primary
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Search,
//                        contentDescription = stringResource(id = R.string.favorites)
//                    )
//                }
            })
        },
    ) { innerPadding ->
//        LazyColumn(
//            modifier = Modifier
//                .padding(dimensionResource(id = R.dimen.body_padding))
//                .fillMaxSize(),
//            contentPadding = innerPadding
//        ) {
//            items(reviews, key = { review -> review.id }) { review ->
//                ReviewCard(
//                    business = review,
//                    onReviewCardClicked = onReviewCardClicked
//                )
//                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.body_padding)))
//            }
//        }
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