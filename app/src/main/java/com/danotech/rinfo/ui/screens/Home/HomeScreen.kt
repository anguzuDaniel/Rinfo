package com.danotech.rinfo.ui.screens.Home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.RinfoAppUiState
import com.danotech.rinfo.ui.components.CategoryIconButton
import com.danotech.rinfo.ui.components.Review
import com.danotech.rinfo.ui.components.ReviewCard
import com.danotech.rinfo.ui.components.RinfoBottomNavigation
import com.danotech.rinfo.ui.components.ShowOptionButton
import com.danotech.rinfo.ui.components.TextInput
import com.danotech.rinfo.ui.screens.RInfoScreen
import com.danotech.rinfo.ui.screens.appbars.CenteredBottomBarLayout
import com.danotech.rinfo.ui.screens.appbars.RinfoTopAppBar
import com.example.compose.AppTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    rinfoAppUiState: RinfoAppUiState,
    currentPage: RInfoScreen,
) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            RinfoTopAppBar(
                isShowingHomePage = true,
                onBackButtonClicked = {
                    // Back button clicked
                },
            )
        },
        bottomBar = {
            CenteredBottomBarLayout(
                bottomBar = {
                    RinfoBottomNavigation(
                        rinfoAppUiState = rinfoAppUiState,
                        currentScreen = RInfoScreen.Start,
                        onTabSelected = {},
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                fab = {
                    FloatingActionButton(
                        onClick = {
                            // FAB onClick
                        },
                        modifier = Modifier.padding(bottom = 10.dp),
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(id = R.string.search)
                        )
                    }
                }
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) { innerPadding ->
        HomePageContent(
            innerPadding = innerPadding
        )
    }
}

/**
 * HomePageContent
 * @param modifier Modifier
 * @param reviews List<Review>
 *
 * shows the home page content
 *
 * contains all the main content of the home page
 * at the top is the serach bar
 * then the category options
 * then the show options
 * then the filter row
 * then the reviews
 * @see ReviewCard
 */
@Composable
fun HomePageContent(
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
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

    LazyColumn(
        modifier = modifier.padding(dimensionResource(id = R.dimen.body_padding)),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = innerPadding,
    ) {
        // Search bar
        item {
            TextInput(
                labelText = "Search",
                leadingIcon = Icons.Default.Search
            )
        }

        item {
            CategoryOptionRow()
        }

        item {
            ShowOptionRow()
        }

        item {
            FilterRow()
        }

        items(reviews, key = { review -> review.id }) { review ->
            ReviewCard(review = review)
        }
    }
}

@Composable
fun FilterRow(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Most Popular",
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = stringResource(R.string.view_all),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }

}

/**
 * CategoryOptionRow
 * @param modifier Modifier
 * @param horizontalArrangement Arrangement.Horizontal
 * @param content @Composable () -> Unit
 *
 * shows the category options
 */
@Composable
fun CategoryOptionRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CategoryIconButton(
            description = "Category",
            icon = R.drawable.baseline_view_module_24,
            name = R.string.all,
            modifier = Modifier.weight(1f)
        )

        CategoryIconButton(
            description = "Category",
            icon = R.drawable.baseline_dining_24,
            name = R.string.restaurants,
            modifier = Modifier.weight(1f)
        )

        CategoryIconButton(
            description = "Category",
            icon = R.drawable.baseline_sports_bar_24,
            name = R.string.bars,
            modifier = Modifier.weight(1f)
        )

        CategoryIconButton(
            description = "Category",
            icon = R.drawable.baseline_local_hotel_24,
            name = R.string.hotels,
            modifier = Modifier.weight(1f)
        )

        CategoryIconButton(
            description = "Category",
            icon = R.drawable.baseline_more_horiz_24,
            name = R.string.others,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun ShowOptionRow() {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ShowOptionButton(
            name = R.string.all,
            active = true,
            onClick = {},
            modifier = Modifier
                .weight(1f)
        )

        Spacer(modifier = Modifier.width(5.dp))

        ShowOptionButton(
            name = R.string.popular,
            active = false,
            onClick = {},
            modifier = Modifier
                .weight(1f)
        )

        Spacer(modifier = Modifier.width(5.dp))

        ShowOptionButton(
            name = R.string.newest,
            active = false,
            onClick = {},
            modifier = Modifier
                .weight(1f)
        )

        Spacer(modifier = Modifier.width(5.dp))

        ShowOptionButton(
            name = R.string.trend,
            active = false,
            onClick = {},
            modifier = Modifier
                .weight(1f)
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    AppTheme {
        HomeScreen(
            rinfoAppUiState = RinfoAppUiState(
                currentScreen = RInfoScreen.Start
            ),
            currentPage = RInfoScreen.Start
        )
    }
}

@Preview
@Composable
fun HomeScreenDarkPreview() {
    AppTheme(
        darkTheme = true,
    ) {
        HomeScreen(
            rinfoAppUiState = RinfoAppUiState(
                currentScreen = RInfoScreen.Start
            ),
            currentPage = RInfoScreen.Start
        )
    }
}