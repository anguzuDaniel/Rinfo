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
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.model.Business
import com.danotech.rinfo.ui.ThemeViewModel
import com.danotech.rinfo.ui.components.RinfoBottomNavigation
import com.danotech.rinfo.ui.screens.RInfoScreen
import com.danotech.rinfo.ui.screens.appbars.CenteredBottomBarLayout
import com.danotech.rinfo.ui.screens.appbars.RinfoTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
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
                                    Text(text = "1")
                                }
                            }) {
                            IconButton(
                                onClick = onChatClick,
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ShoppingCart,
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
        HomeContent(
            viewModel = viewModel,
            themeViewModel = themeViewModel,
            innerPadding = innerPadding,
            onReviewCardClicked = onReviewCardClicked,
            modifier = Modifier.consumeWindowInsets(innerPadding)
        ) { onCategoryClicked() }
    }
}