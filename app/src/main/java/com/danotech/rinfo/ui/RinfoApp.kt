package com.danotech.rinfo.ui

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.danotech.rinfo.RinfoViewModel
import com.danotech.rinfo.ui.components.Review
import com.danotech.rinfo.ui.screens.Home.HomeScreen
import com.danotech.rinfo.ui.screens.RInfoScreen
import com.danotech.rinfo.ui.screens.account.CreateAccount
import com.danotech.rinfo.ui.screens.account.CreateAccountUiState
import com.danotech.rinfo.ui.screens.account.Login
import com.danotech.rinfo.ui.screens.category.MoreCategoriesPage
import com.danotech.rinfo.ui.screens.favorites.FavoriteScreen
import com.danotech.rinfo.ui.screens.notification.NotificationPage
import com.danotech.rinfo.ui.screens.review.ReviewScreen
import com.danotech.rinfo.ui.screens.search.SearchCategory
import com.danotech.rinfo.ui.screens.search.SearchPage
import com.danotech.rinfo.ui.screens.settings.SettingPage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun RinfoApp(
    navController: NavHostController = rememberNavController()
) {
    val activity = LocalContext.current as Activity

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val viewModel: RinfoViewModel = viewModel()
    val rinfoAppUiState = viewModel.uiState.collectAsState().value

    NavHost(
        navController = navController,
        startDestination = RInfoScreen.Home.name
    ) {
        composable(route = RInfoScreen.Home.name) {
            HomeScreen(
                rinfoAppUiState = rinfoAppUiState,
                currentPage = rinfoAppUiState.currentScreen,
                onTabSelected = { screen ->
                    if (Firebase.auth.currentUser == null) {
                        navController.navigate(RInfoScreen.Login.name)
                        return@HomeScreen
                    }

                    navController.navigate(screen.name)
                },
                onBackPressed = {
                    activity.finish()
                },
                onReviewCardClicked = { review: Review ->
                    if (Firebase.auth.currentUser == null) {
                        navController.navigate(RInfoScreen.Login.name)
                        return@HomeScreen
                    }

                    viewModel.showBusinessDetails(
                        review = review,
                    )
                    navController.navigate(RInfoScreen.Review.name)
                },
                onFabClicked = {
                    if (Firebase.auth.currentUser == null) {
                        navController.navigate(RInfoScreen.Login.name)
                        return@HomeScreen
                    }

                    navController.navigate(RInfoScreen.Search.name)
                },
                onCategoryClicked = {
                    if (Firebase.auth.currentUser == null) {
                        navController.navigate(RInfoScreen.Login.name)
                        return@HomeScreen
                    }

                    navController.navigate(RInfoScreen.MoreCategories.name)
                },
                onSearchInputClicked = {
                    if (Firebase.auth.currentUser == null) {
                        navController.navigate(RInfoScreen.Login.name)
                        return@HomeScreen
                    }

                    navController.navigate(RInfoScreen.Search.name)
                },
            )
        }
        composable(route = RInfoScreen.Login.name) {
            Login(
                onSignUpTextClicked = {
                    navController.navigate(RInfoScreen.Account.name)
                },
                onBackHandler = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = RInfoScreen.Account.name) {
            CreateAccount(
                onSignInTextClicked = {
                    navController.navigate(RInfoScreen.Login.name)
                },
                createAccountUiState = CreateAccountUiState(),
                onBackHandler = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = RInfoScreen.Favourites.name) {
            FavoriteScreen(
                rinfoAppUiState = rinfoAppUiState,
                onBackPressed = {
                    navController.popBackStack()
                },
                onFabClicked = {
                    navController.navigate(RInfoScreen.Search.name)
                },
                onTabSelected = { screen ->
                    navController.navigate(screen.name)
                },
                onReviewCardClicked = { review: Review ->
                    viewModel.showBusinessDetails(
                        review = review,
                    )
                    navController.navigate(RInfoScreen.Review.name)
                }
            )
        }
        composable(route = RInfoScreen.Notification.name) {
            NotificationPage(
                rinfoAppUiState = rinfoAppUiState,
                onBackPressed = {
                    navController.popBackStack()
                },
                onFabClicked = {
                    viewModel.onScreenSelected(RInfoScreen.Search)
                },
                onTabSelected = { screen ->
                    navController.navigate(screen.name)
                },
            )
        }
        composable(route = RInfoScreen.Settings.name) {
            SettingPage(
                rinfoAppUiState = rinfoAppUiState,
                onBackPressed = {
                    navController.popBackStack()
                },
                onFabClicked = {
                    viewModel.onScreenSelected(RInfoScreen.Search)
                },
                onTabSelected = { screen ->
                    navController.navigate(screen.name)
                },
            )
        }
        composable(route = RInfoScreen.Search.name) {
            SearchPage(
                onBackPressed = {
                    navController.popBackStack()
                },
            )
        }
        composable(route = RInfoScreen.MoreCategories.name) {
            MoreCategoriesPage(
                onBackPressed = {
                    navController.popBackStack()
                },
                onCategoryItemClicked = {
//                    viewModel.onCategorySelected(it)
                    navController.navigate(RInfoScreen.Category.name)
                }
            )
        }
        composable(route = RInfoScreen.Category.name) {
            SearchCategory(
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = RInfoScreen.Review.name) {
            ReviewScreen(
                rinfoAppUiState = rinfoAppUiState,
                onBackPressed = {
                    navController.popBackStack()
                },
            )
        }
    }
}