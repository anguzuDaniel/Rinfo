@file:Suppress("KDocUnresolvedReference")
@file:OptIn(ExperimentalMaterial3Api::class)

package com.danotech.rinfo.ui.screens.home

import android.annotation.SuppressLint
import android.os.Build
import android.view.Window
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.model.Business
import com.danotech.rinfo.ui.ThemeViewModel
import com.danotech.rinfo.ui.components.BusinessCard
import com.danotech.rinfo.ui.components.BusinessCardShimmer
import com.danotech.rinfo.ui.components.FilterRow
import com.danotech.rinfo.ui.components.RinfoBottomNavigation
import com.danotech.rinfo.ui.screens.RInfoScreen
import com.danotech.rinfo.ui.screens.appbars.CenteredBottomBarLayout
import com.danotech.rinfo.ui.screens.appbars.RinfoTopAppBar

@RequiresApi(Build.VERSION_CODES.P)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    viewModel: HomesScreenViewModel = hiltViewModel(),
    themeViewModel: ThemeViewModel = hiltViewModel(),
    onTabSelected: (RInfoScreen) -> Unit = {},
    onBackPressed: () -> Unit = {},
    onReviewCardClicked: (Business) -> Unit = {},
    onCategoryClicked: () -> Unit = {},
    window: Window,
    onChatClick: () -> Unit = {},
    onNotificationClicked: () -> Unit = {} // NOTE: this should always be the last
) {
    BackHandler {
        onBackPressed()
    }

    val view = LocalView.current
    val windowInsetsController =
        WindowCompat.getInsetsController(window, view)


    val useDarkIcons = themeViewModel.themeState.value.isDarkMode

    LaunchedEffect(useDarkIcons) {
        windowInsetsController.isAppearanceLightStatusBars = !useDarkIcons
        window.statusBarColor = Color.Transparent.toArgb()
        window.navigationBarDividerColor = Color.White.toArgb()
    }

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            RinfoTopAppBar(
                isShowingHomePage = true,
                onBackButtonClicked = {},
                isSearchPage = true,
                actions = {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        BadgedBox(
                            badge = {
                                Badge {
                                    Text(text = "8")
                                }
                            }) {
                            IconButton(
                                onClick = onChatClick,
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.Chat,
                                    contentDescription = "Messages",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
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
    val businesses = viewModel.businessFlow.collectAsState(initial = emptyList()).value
    val listState = rememberLazyListState()

    val paddingVertical = R.dimen.body_padding

    Surface(
        modifier = Modifier.nestedScroll(rememberNestedScrollInteropConnection()),
    ) {
        LazyColumn(
            modifier = modifier
                .height(LocalConfiguration.current.screenHeightDp.dp)
                .padding(bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(top = innerPadding.calculateTopPadding()),
            state = listState,
        ) {
            item {
                FilterBusinessRow(
                    paddingHorizontal = R.dimen.body_padding_small,
                    paddingStart = R.dimen.body_padding
                )
            }
//
//            item {
//                CategoryOptionRow(
//                    onCategoryClicked = { onCategoryClicked() },
//                    paddingHorizontal = R.dimen.body_padding,
//                    paddingVertical = R.dimen.body_padding_small
//                )
//            }

            item {
                FilterRow(
                    paddingHorizontal = R.dimen.body_padding
                )
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
}

