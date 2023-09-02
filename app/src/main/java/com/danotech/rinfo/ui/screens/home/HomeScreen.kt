package com.danotech.rinfo.ui.screens.home

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.model.Business
import com.danotech.rinfo.ui.components.CategoryIconButton
import com.danotech.rinfo.ui.components.LoadingCard
import com.danotech.rinfo.ui.components.ReviewCard
import com.danotech.rinfo.ui.components.RinfoBottomNavigation
import com.danotech.rinfo.ui.components.ShowOptionButton
import com.danotech.rinfo.ui.screens.RInfoScreen
import com.danotech.rinfo.ui.screens.appbars.CenteredBottomBarLayout
import com.danotech.rinfo.ui.screens.appbars.RinfoTopAppBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    viewModel: HomesScreenViewModel = hiltViewModel(),
    onTabSelected: (RInfoScreen) -> Unit = {},
    onBackPressed: () -> Unit = {},
    onFabClicked: () -> Unit = {},
    onReviewCardClicked: (Business) -> Unit = {},
    onFilterClicked: () -> Unit = {},
    onCategoryClicked: () -> Unit = {},
    onNotificationClicked: () -> Unit = {},
) {
    BackHandler {
        onBackPressed()
    }

    var searchQuery by remember { mutableStateOf(TextFieldValue()) }
    var searchResults by remember { mutableStateOf(emptyList<String>()) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            RinfoTopAppBar(
                isShowingHomePage = true,
                onBackButtonClicked = {},
                isSearchPage = true,
                actions = {
                    IconButton(
                        onClick = onNotificationClicked,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Notifications,
                            contentDescription = "notifications",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                modifier = Modifier.background(MaterialTheme.colorScheme.primary)
            )
        },
        bottomBar = {
            CenteredBottomBarLayout(
                bottomBar = {
                    RinfoBottomNavigation(
                        currentScreen = RInfoScreen.Home,
                        onTabSelected = onTabSelected,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                fab = {}
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) { innerPadding ->
        HomePageContent(
            viewModel = viewModel,
            innerPadding = innerPadding,
            onReviewCardClicked = onReviewCardClicked,
            onCategoryClicked = { onCategoryClicked() },
            onFilterClicked = onFilterClicked,
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
    viewModel: HomesScreenViewModel,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier,
    onReviewCardClicked: (Business) -> Unit = {},
    onBackPressed: () -> Unit = {},
    onCategoryClicked: () -> Unit = {},
    onFilterClicked: () -> Unit = {},
) {
    val reviews = viewModel.showReviews()
    val businessState by viewModel.businessFlow.collectAsState(initial = emptyList())


    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.body_padding)),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = innerPadding,
    ) {
        item {
            CategoryOptionRow(
                onCategoryClicked = { onCategoryClicked() }
            )
        }

        item {
            ShowOptionRow(
                onFilterClicked = onFilterClicked
            )
        }

        item {
            FilterRow()
        }

        if (viewModel.uiState.value.isLoading) {
            item {
                LoadingCard()
            }
        } else {
            items(businessState) { business ->
                ReviewCard(
                    business = business,
                    onReviewCardClicked = onReviewCardClicked,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterRow(
    heading: String = stringResource(R.string.most_popular),
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = heading,
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
fun CategoryOptionRow(
    onCategoryClicked: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CategoryIconButton(
            description = "Category",
            icon = R.drawable.baseline_view_module_24,
            name = R.string.all,
            modifier = Modifier.weight(1f),
            onCategoryClicked = { onCategoryClicked() }
        )

        CategoryIconButton(
            description = "Category",
            icon = R.drawable.baseline_dining_24,
            name = R.string.restaurants,
            modifier = Modifier.weight(1f),
            onCategoryClicked = { onCategoryClicked() }
        )

        CategoryIconButton(
            description = "Category",
            icon = R.drawable.baseline_sports_bar_24,
            name = R.string.bars,
            modifier = Modifier.weight(1f),
            onCategoryClicked = { onCategoryClicked() }
        )

        CategoryIconButton(
            description = "Category",
            icon = R.drawable.baseline_local_hotel_24,
            name = R.string.hotels,
            modifier = Modifier.weight(1f),
            onCategoryClicked = { onCategoryClicked() }
        )

        CategoryIconButton(
            description = "Category",
            icon = R.drawable.baseline_more_horiz_24,
            name = R.string.others,
            modifier = Modifier.weight(1f),
            onCategoryClicked = { onCategoryClicked() }
        )
    }
}

@Composable
fun ShowOptionRow(
    onFilterClicked: () -> Unit = {}
) {

    var clicked by remember { mutableStateOf(false) }

    var selected by remember { mutableStateOf(false) }


    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ShowOptionButton(
            name = R.string.popular,
            active = clicked,
            modifier = Modifier
                .weight(1f),
            onFilterClicked = {
                clicked = !clicked
                onFilterClicked()
            }
        )

        ShowOptionButton(
            name = R.string.latest,
            active = clicked,
            modifier = Modifier
                .weight(1f),
            onFilterClicked = {
                clicked = !clicked
                onFilterClicked()
            }
        )

        ShowOptionButton(
            name = R.string.trending,
            active = clicked,
            modifier = Modifier
                .weight(1f),
            onFilterClicked = {
                clicked = !clicked
                onFilterClicked()
            }
        )

        ShowOptionButton(
            name = R.string.affordable,
            active = clicked,
            modifier = Modifier
                .weight(1f),
            onFilterClicked = {
                clicked = !clicked
                onFilterClicked()
            }
        )
    }
}
