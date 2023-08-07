package com.danotech.rinfo.ui

import android.Manifest
import android.content.res.Resources
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.danotech.rinfo.common.SnackbarManager
import com.danotech.rinfo.common.composable.PermissionDialog
import com.danotech.rinfo.common.composable.RationaleDialog
import com.danotech.rinfo.ui.components.Review
import com.danotech.rinfo.ui.screens.RInfoScreen
import com.danotech.rinfo.ui.screens.account.CreateAccount
import com.danotech.rinfo.ui.screens.login.LoginScreen
import com.danotech.rinfo.ui.screens.category.MoreCategoriesPage
import com.danotech.rinfo.ui.screens.favorites.FavoriteScreen
import com.danotech.rinfo.ui.screens.home.HomeScreen
import com.danotech.rinfo.ui.screens.notification.NotificationPage
import com.danotech.rinfo.ui.screens.review.ReviewScreen
import com.danotech.rinfo.ui.screens.search.SearchCategory
import com.danotech.rinfo.ui.screens.search.SearchPage
import com.danotech.rinfo.ui.screens.settings.SettingPage
import com.danotech.rinfo.ui.theme.AppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RinfoApp() {
    AppTheme() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            RequestNotificationPermissionDialog()
        }

        Surface(color = MaterialTheme.colors.background) {
            val appState = rememberAppState()

            Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = it,
                        modifier = Modifier.padding(8.dp),
                        snackbar = { snackbarData ->
                            Snackbar(snackbarData, contentColor = MaterialTheme.colors.onPrimary)
                        }
                    )
                },
                scaffoldState = appState.scaffoldState
            ) { innerPaddingModifier ->
                NavHost(
                    navController = appState.navController,
                    startDestination = RInfoScreen.Home.name,
                    modifier = Modifier.padding(innerPaddingModifier)
                ) {
                    makeItSoGraph(appState)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestNotificationPermissionDialog() {
    val permissionState =
        rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

    if (!permissionState.status.isGranted) {
        if (permissionState.status.shouldShowRationale) RationaleDialog()
        else PermissionDialog { permissionState.launchPermissionRequest() }
    }
}

@Composable
fun rememberAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) =
    remember(scaffoldState, navController, snackbarManager, resources, coroutineScope) {
        RinfoAppUiState(
            scaffoldState = scaffoldState,
            navController = navController,
            snackbarManager = snackbarManager,
            resources = resources,
            coroutineScope = coroutineScope
        )
    }

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}

@ExperimentalMaterialApi
fun NavGraphBuilder.makeItSoGraph(appState: RinfoAppUiState) {
    composable(route = RInfoScreen.Home.name) {
        HomeScreen(
            onTabSelected = { screen ->
                if (Firebase.auth.currentUser == null) {
                    appState.navigate(RInfoScreen.Login.name)
                    return@HomeScreen
                }

                appState.navigate(screen.name)
            },
            onBackPressed = {
                appState.popUp()
            },
            onReviewCardClicked = { review: Review ->
                if (Firebase.auth.currentUser == null) {
                    appState.navigate(RInfoScreen.Login.name)
                    return@HomeScreen
                }

                appState.navigate(RInfoScreen.Review.name)
            },
            onFabClicked = {
                if (Firebase.auth.currentUser == null) {
                    appState.navigate(RInfoScreen.Login.name)
                    return@HomeScreen
                }

                appState.navigate(RInfoScreen.Search.name)
            },
            onCategoryClicked = {
                if (Firebase.auth.currentUser == null) {
                    appState.navigate(RInfoScreen.Login.name)
                    return@HomeScreen
                }

                appState.navigate(RInfoScreen.MoreCategories.name)
            },
            onSearchInputClicked = {
                if (Firebase.auth.currentUser == null) {
                    appState.navigate(RInfoScreen.Login.name)
                    return@HomeScreen
                }

                appState.navigate(RInfoScreen.Search.name)
            },
        )
    }
    composable(route = RInfoScreen.Login.name) {
        LoginScreen(
            onSignUpTextClicked = {
                appState.navigate(RInfoScreen.Account.name)
            },
            onBackHandler = {
                appState.popUp()
            },
            onSignInClick = {
                appState.navigate(RInfoScreen.Home.name)
            }
        )
    }
    composable(route = RInfoScreen.Account.name) {
        CreateAccount(
            onSignInTextClicked = {
                appState.navigate(RInfoScreen.Login.name)
            },
            onBackHandler = {
                appState.popUp()
            }
        )
    }
    composable(route = RInfoScreen.Favourites.name) {
        FavoriteScreen(
            onBackPressed = {
                appState.popUp()
            },
            onFabClicked = {
                appState.navigate(RInfoScreen.Search.name)
            },
            onTabSelected = { screen ->
                appState.navigate(screen.name)
            },
            onReviewCardClicked = { review: Review ->

                appState.navigate(RInfoScreen.Review.name)
            }
        )
    }
    composable(route = RInfoScreen.Notification.name) {
        NotificationPage(
            onBackPressed = {
                appState.popUp()
            },
            onFabClicked = {
                appState.navigate(RInfoScreen.Search.name)
            },
            onTabSelected = { screen ->
                appState.navigate(screen.name)
            },
        )
    }
    composable(route = RInfoScreen.Settings.name) {
        SettingPage(
            onBackPressed = {
                appState.popUp()
            },
            onFabClicked = {
                appState.navigate(RInfoScreen.Search.name)
            },
            onTabSelected = { screen ->
                appState.navigate(screen.name)
            },
        )
    }
    composable(route = RInfoScreen.Search.name) {
        SearchPage(
            onBackPressed = {
                appState.popUp()
            },
        )
    }
    composable(route = RInfoScreen.MoreCategories.name) {
        MoreCategoriesPage(
            onBackPressed = {
                appState.popUp()
            },
            onCategoryItemClicked = {
                appState.navigate(RInfoScreen.Category.name)
            }
        )
    }
    composable(route = RInfoScreen.Category.name) {
        SearchCategory(
            onBackPressed = {
                appState.popUp()
            }
        )
    }
    composable(route = RInfoScreen.Review.name) {
        ReviewScreen(
            onBackPressed = {
                appState.popUp()
            },
        )
    }
}
