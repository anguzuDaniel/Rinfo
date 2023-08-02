package com.danotech.rinfo.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.danotech.rinfo.RinfoViewModel
import com.danotech.rinfo.ui.screens.Home.HomeScreen

@Composable
fun RinfoApp(
    navController: NavHostController = rememberNavController()
) {
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
    HomeScreen(
        rinfoAppUiState = rinfoAppUiState,
        currentPage = rinfoAppUiState.currentScreen
    )
}