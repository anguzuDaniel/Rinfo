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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.danotech.rinfo.common.SnackbarManager
import com.danotech.rinfo.common.composable.PermissionDialog
import com.danotech.rinfo.common.composable.RationaleDialog
import com.danotech.rinfo.model.Business
import com.danotech.rinfo.ui.components.Review
import com.danotech.rinfo.ui.screens.RInfoScreen
import com.danotech.rinfo.ui.screens.account.CreateAccount
import com.danotech.rinfo.ui.screens.business_account.BusinessAccount
import com.danotech.rinfo.ui.screens.category.CategoryScreen
import com.danotech.rinfo.ui.screens.favorites.FavoriteScreen
import com.danotech.rinfo.ui.screens.home.HomeScreen
import com.danotech.rinfo.ui.screens.login.LoginScreen
import com.danotech.rinfo.ui.screens.notification.NotificationPage
import com.danotech.rinfo.ui.screens.profile.ProfileScreen
import com.danotech.rinfo.ui.screens.review.ReviewScreen
import com.danotech.rinfo.ui.screens.selected_category.SelectedCategoryScreen
import com.danotech.rinfo.ui.screens.search_business.SearchPage
import com.danotech.rinfo.ui.screens.settings.SettingsScreen
import com.danotech.rinfo.ui.theme.AppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RinfoApp() {
    AppTheme {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            RequestNotificationPermissionDialog()
        }

        Surface(color = MaterialTheme.colors.background) {
            val appState = rememberAppState()

            Scaffold(
                snackbarHost = {
                    SnackbarHost(hostState = it,
                        modifier = Modifier.padding(8.dp),
                        snackbar = { snackbarData ->
                            Snackbar(snackbarData, contentColor = MaterialTheme.colors.onPrimary)
                        })
                }, scaffoldState = appState.scaffoldState
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
) = remember(scaffoldState, navController, snackbarManager, resources, coroutineScope) {
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
                if (!appState.isLoggedIn) {
                    appState.navigate(RInfoScreen.Login.name)
                    return@HomeScreen
                }

                appState.navigate(screen.name)
            },
            onBackPressed = {
                appState.popUp()
            },
            onReviewCardClicked = { business: Business ->
                if (!appState.isLoggedIn) {
                    appState.navigate(RInfoScreen.Login.name)
                    return@HomeScreen
                }

                appState.navigate("${RInfoScreen.Review.name}/${business.id}")
            },
            onFabClicked = {
                if (!appState.isLoggedIn) {
                    appState.navigate(RInfoScreen.Login.name)
                    return@HomeScreen
                }

                appState.navigate(RInfoScreen.Search.name)
            },
            onCategoryClicked = {
                if (!appState.isLoggedIn) {
                    appState.navigate(RInfoScreen.Login.name)
                    return@HomeScreen
                }

                appState.navigate(RInfoScreen.Categories.name)
            },
            onSearchIconClicked = {
                if (!appState.isLoggedIn) {
                    appState.navigate(RInfoScreen.Login.name)
                    return@HomeScreen
                }

                appState.navigate(RInfoScreen.Search.name)
            },
        )
    }
    composable(route = RInfoScreen.Login.name) {
        LoginScreen(onSignUpTextClicked = {
            appState.navigate(RInfoScreen.Account.name)
        }, onBackHandler = {
            appState.popUp()
        }, openAndPopUp = { route, popUp ->
            appState.navigateAndPopUp(route, popUp)
            appState.logIn()
        })
    }
    composable(route = RInfoScreen.Account.name) {
        CreateAccount(onSignInTextClicked = {
            appState.navigate(RInfoScreen.Login.name)
        }, onBackHandler = {
            appState.popUp()
        })
    }
    composable(route = RInfoScreen.BusinessAccount.name) {
        BusinessAccount(
            onBackClicked = {
                appState.popUp()
            }
        )
    }
    composable(route = RInfoScreen.EditAccount.name) {
        ProfileScreen(
            onBackClicked = {
                appState.popUp()
            }
        )
    }
    composable(route = RInfoScreen.Favourites.name) {
        FavoriteScreen(onBackPressed = {
            appState.popUp()
        }, onFabClicked = {
            appState.navigate(RInfoScreen.Search.name)
        }, onTabSelected = { screen ->
            appState.navigate(screen.name)
        }, onReviewCardClicked = { review: Review ->
            appState.navigate(RInfoScreen.Review.name)
        })
    }
    composable(route = RInfoScreen.Notification.name) {
        NotificationPage(
            onBackPressed = {
                appState.popUp()
            },
            onTabSelected = { screen ->
                appState.navigate(screen.name)
            },
        )
    }
    composable(route = RInfoScreen.Settings.name) {
        SettingsScreen(
            onBackPressed = {
                appState.popUp()
            },
            onFabClicked = {
                appState.navigate(RInfoScreen.Search.name)
            },
            onTabSelected = { screen ->
                appState.navigate(screen.name)
            },
            openAndPopUp = { route, popUp ->
                appState.navigateAndPopUp(route, popUp)
            },
            onLogoutClicked = {
                appState.logOut()
            },
            onNavClicked = { screen ->
                appState.navigate(screen)
            }
        )
    }
    composable(route = RInfoScreen.Search.name) {
        SearchPage(
            onBackPressed = {
                appState.popUp()
            },
        )
    }
    composable(route = RInfoScreen.Categories.name) {
        CategoryScreen(onBackPressed = {
            appState.popUp()
        }, onCategoryItemClicked = {
            appState.navigate(RInfoScreen.SelectedCategory.name)
        })
    }
    composable(route = RInfoScreen.SelectedCategory.name) {
        SelectedCategoryScreen(onBackPressed = {
            appState.popUp()
        })
    }
    composable(
        route = "${RInfoScreen.Review.name}/{businessId}",
        arguments = listOf(navArgument("businessId") { type = NavType.StringType })
    ) { backStackEntry ->
        val businessId = backStackEntry.arguments?.getString("businessId")

        if (businessId != null) {
            // Use the businessId to fetch data or perform other operations
            ReviewScreen(
                businessId = businessId,
                onBackPressed = {
                    appState.popUp()
                }
            )
        } else {
            // Handle the case where businessId is null
        }
    }
}
