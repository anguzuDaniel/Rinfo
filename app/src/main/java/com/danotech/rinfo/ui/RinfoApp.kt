package com.danotech.rinfo.ui

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.danotech.rinfo.RinfoViewModel
import com.danotech.rinfo.ui.screens.Home.HomeScreen
import com.danotech.rinfo.ui.screens.RInfoScreen
import com.danotech.rinfo.ui.screens.account.CreateAccountPage
import com.danotech.rinfo.ui.screens.account.LoginPage
import com.danotech.rinfo.ui.screens.favorites.FavoriteScreen
import com.danotech.rinfo.ui.screens.notification.NotificationPage
import com.danotech.rinfo.ui.screens.review.ReviewScreen
import com.danotech.rinfo.ui.screens.search.SearchPage
import com.danotech.rinfo.ui.screens.settings.SettingPage

@Composable
fun RinfoApp(
    navController: NavHostController = rememberNavController()
) {
    val activity = LocalContext.current as Activity

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val viewModel: RinfoViewModel = viewModel()
    val rinfoAppUiState = viewModel.uiState.collectAsState().value

//    NavHost(
//        navController = navController,
//        startDestination = RInfoScreen.Start.name
//    ) {
//        composable(route = RInfoScreen.Start.name) {
//            HomeScreen(
//                rinfoAppUiState = RinfoAppUiState(
//                    currentScreen = RInfoScreen.Start
//                ),
//            )
//        }
//        composable(route = RInfoScreen.Login.name) {
//            LoginPage()
//        }
//        composable(route = RInfoScreen.Account.name) {
//            CreateAccountPage()
//        }
//        composable(route = RInfoScreen.Search.name) {
//            SearchPage()
//        }
//        composable(route = RInfoScreen.Favourite.name) {
//            FavoriteScreen()
//        }
//        composable(route = RInfoScreen.Notification.name) {
//            NotificationPage(
//                rinfoAppUiState = RinfoAppUiState(
//                    currentScreen = RInfoScreen.Notification
//                )
//            )
//        }
//        composable(route = RInfoScreen.Settings.name) {
//            SettingPage()
//        }
//        composable(route = RInfoScreen.Review.name) {
//            ReviewScreen()
//        }
//    }

    when (rinfoAppUiState.currentScreen.name) {
        RInfoScreen.Login.name -> {
            LoginPage(
                rinfoAppUiState = rinfoAppUiState
            )
        }

        RInfoScreen.Account.name -> {
            CreateAccountPage()
        }

        RInfoScreen.Search.name -> {
            SearchPage()
        }

        RInfoScreen.Favourites.name -> {
            FavoriteScreen(
                rinfoAppUiState = rinfoAppUiState,
                onBackPressed = {
                    viewModel.popBackStack()
                },
                onTabSelected = { screen ->
                    viewModel.onScreenSelected(screen)
                },
            )
        }

        RInfoScreen.Notification.name -> {
            NotificationPage(
                rinfoAppUiState = rinfoAppUiState,
                onBackPressed = {
                    viewModel.popBackStack()
                },
                onTabSelected = { screen ->
                    viewModel.onScreenSelected(screen)
                },
            )
        }

        RInfoScreen.Settings.name -> {
            SettingPage(
                rinfoAppUiState = rinfoAppUiState,
                onBackPressed = {
                    viewModel.popBackStack()
                },
                onTabSelected = { screen ->
                    viewModel.onScreenSelected(screen)
                },
            )
        }

        RInfoScreen.Review.name -> {
            ReviewScreen()
        }

        else -> {
            HomeScreen(
                rinfoAppUiState = rinfoAppUiState,
                currentPage = rinfoAppUiState.currentScreen,
                onTabSelected = { screen ->
                    viewModel.onScreenSelected(screen)
                },
                onBackPressed = {
                    activity.finish()
                }
            )
        }
    }
}