@file:Suppress("KDocUnresolvedReference")

package com.danotech.rinfo.ui.screens.home

import android.annotation.SuppressLint
import android.os.Build
import android.view.Window
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.model.Business
import com.danotech.rinfo.ui.components.BusinessCard
import com.danotech.rinfo.ui.components.BusinessCardShimmer
import com.danotech.rinfo.ui.components.RinfoBottomNavigation
import com.danotech.rinfo.ui.screens.RInfoScreen
import com.danotech.rinfo.ui.screens.appbars.CenteredBottomBarLayout
import com.danotech.rinfo.ui.screens.appbars.RinfoTopAppBar

@RequiresApi(Build.VERSION_CODES.P)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    viewModel: HomesScreenViewModel = hiltViewModel(),
    onTabSelected: (RInfoScreen) -> Unit = {},
    onBackPressed: () -> Unit = {},
    onReviewCardClicked: (Business) -> Unit = {},
    onCategoryClicked: () -> Unit = {},
    window: Window,
    onNotificationClicked: () -> Unit = {},
) {
    BackHandler {
        onBackPressed()
    }

    val view = LocalView.current
    val windowInsetsController =
        WindowCompat.getInsetsController(window, view)

    val useDarkIcons = !isSystemInDarkTheme()

    LaunchedEffect(Unit) {
        windowInsetsController.isAppearanceLightStatusBars = useDarkIcons
        window.statusBarColor = Color.Transparent.toArgb()
        window.navigationBarDividerColor = Color.White.toArgb()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            RinfoTopAppBar(
                isShowingHomePage = true,
                onBackButtonClicked = {},
                isSearchPage = true,
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        IconButton(
                            onClick = onNotificationClicked,
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Notifications,
                                contentDescription = "notifications",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }

                        IconButton(
                            onClick = onNotificationClicked,
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Message,
                                contentDescription = "Messages",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                },
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
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
            modifier = Modifier.consumeWindowInsets(innerPadding)
        ) { onCategoryClicked() }
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
 * at the top is the search bar
 * then the category options
 * then the show options
 * then the filter row
 * then the reviews
 * @see BusinessCard
 */
@Composable
fun HomePageContent(
    viewModel: HomesScreenViewModel,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier,
    onReviewCardClicked: (Business) -> Unit = {},
    onCategoryClicked: () -> Unit = {},
) {
    viewModel.showReviews()
    val businesses by viewModel.businessFlow.collectAsState(initial = emptyList())

    val listState = rememberLazyListState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.body_padding)),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = innerPadding,
        state = listState,
        userScrollEnabled = false
    ) {
        item {
            CategoryOptionRow(
                onCategoryClicked = { onCategoryClicked() },
            )
        }

        item {
            FilterBusinessRow(
            )
        }

        item {
            FilterRow()
        }

        items(businesses) { business ->
            BusinessCardShimmer(
                isLoading = viewModel.uiState.value.isLoading
            ) {
                BusinessCard(
                    business = business,
                    onReviewCardClicked = onReviewCardClicked,
                )
            }
        }
    }
}

@Composable
fun FilterRow(
    modifier: Modifier = Modifier,
    heading: String = stringResource(R.string.most_popular),
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
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