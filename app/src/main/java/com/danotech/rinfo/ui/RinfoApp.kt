package com.danotech.rinfo.ui

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import android.Manifest
import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Build
import android.view.Window
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ScaffoldState
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
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
import com.danotech.rinfo.model.Review
import com.danotech.rinfo.ui.screens.RInfoScreen
import com.danotech.rinfo.ui.screens.about.AboutAppScreen
import com.danotech.rinfo.ui.screens.about.AboutScreen
import com.danotech.rinfo.ui.screens.about.PrivacyPolicyScreen
import com.danotech.rinfo.ui.screens.about.TermsOfUseScreen
import com.danotech.rinfo.ui.screens.account.ChangePasswordScreen
import com.danotech.rinfo.ui.screens.account.CreateAccount
import com.danotech.rinfo.ui.screens.business.BusinessScreen
import com.danotech.rinfo.ui.screens.business_account.BusinessAccount
import com.danotech.rinfo.ui.screens.category.CategoryScreen
import com.danotech.rinfo.ui.screens.favorites.FavoriteScreen
import com.danotech.rinfo.ui.screens.home.HomeScreen
import com.danotech.rinfo.ui.screens.login.LoginScreen
import com.danotech.rinfo.ui.screens.map.MapScreen
import com.danotech.rinfo.ui.screens.notification.NotificationPage
import com.danotech.rinfo.ui.screens.profile.ProfileScreen
import com.danotech.rinfo.ui.screens.review.ReviewForm
import com.danotech.rinfo.ui.screens.review.ReviewsScreen
import com.danotech.rinfo.ui.screens.search_business.SearchPage
import com.danotech.rinfo.ui.screens.selected_category.SelectedCategoryScreen
import com.danotech.rinfo.ui.screens.settings.AccountOptionsScreen
import com.danotech.rinfo.ui.screens.settings.SettingsScreen
import com.danotech.rinfo.ui.theme.AppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RinfoApp(
    window: Window
) {
    AppTheme {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            RequestNotificationPermissionDialog()
        }

        val view = LocalView.current
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemGestures())
            // Apply the insets as padding to the view. Here we're setting all of the
            // dimensions, but apply as appropriate to your layout. You could also
            // update the views margin if more appropriate.
            v.updatePadding(insets.left, insets.top, insets.right, insets.bottom)

            // Return CONSUMED if we don't want the window insets to keep being passed
            // down to descendant views.
            WindowInsetsCompat.CONSUMED
        }

        val windowInsetsController =
            WindowCompat.getInsetsController(window, view)

        // Hide the system bars.
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())

        // Show the system bars.
        windowInsetsController.show(WindowInsetsCompat.Type.systemBars())

        window.statusBarColor = Color.Transparent.toArgb()
        windowInsetsController.isAppearanceLightStatusBars = true

        // Remove the color from the navigation bar (if needed)
        window.navigationBarColor = Color.Transparent.toArgb()

        val snackbarHostState = remember { SnackbarHostState() }

        Surface(color = MaterialTheme.colorScheme.background) {
            val appState = rememberAppState()

            Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = snackbarHostState,
                        modifier = Modifier.padding(8.dp),
                        snackbar = { snackbarData ->
                            Snackbar(
                                snackbarData,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        })
                },
            ) {
                NavHost(
                    navController = appState.navController,
                    startDestination = RInfoScreen.Home.name,
                ) {
                    makeItSoGraph(
                        appState,
                        window = window
                    )
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

@RequiresApi(Build.VERSION_CODES.Q)
@ExperimentalMaterialApi
fun NavGraphBuilder.makeItSoGraph(
    appState: RinfoAppUiState,
    window: Window
) {
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

                appState.navigate("${RInfoScreen.Business.name}/${business.id}")
            },
            onCategoryClicked = {
                if (!appState.isLoggedIn) {
                    appState.navigate(RInfoScreen.Login.name)
                    return@HomeScreen
                }

                appState.navigate(RInfoScreen.Categories.name)
            },
            window = window
        ) {
            if (!appState.isLoggedIn) {
                appState.navigate(RInfoScreen.Login.name)
                return@HomeScreen
            }

            appState.navigate(RInfoScreen.Notification.name)
        }
    }
    composable(route = RInfoScreen.Login.name) {
        LoginScreen(onSignUpTextClicked = {
            appState.navigate(RInfoScreen.CreateAccount.name)
        }, onBackHandler = {
            appState.popUp()
        }, openAndPopUp = { route, popUp ->
            appState.navigateAndPopUp(route, popUp)
            appState.logIn()
        })
    }
    composable(route = RInfoScreen.CreateAccount.name) {
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
        }, onReviewCardClicked = { _: Review ->
            appState.navigate(RInfoScreen.Business.name)
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
    composable(route = RInfoScreen.Account.name) {
        AccountOptionsScreen(
            onBackPressed = {
                appState.popUp()
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
        route = "${RInfoScreen.Business.name}/{businessId}",
        arguments = listOf(navArgument("businessId") { type = NavType.StringType })
    ) { backStackEntry ->
        val businessId = backStackEntry.arguments?.getString("businessId")

        if (businessId != null) {
            // Use the businessId to fetch data or perform other operations
            BusinessScreen(
                businessId = businessId,
                onBackPressed = {
                    appState.popUp()
                },
                onFabBtnClicked = {
                    // send user to review form
                    // width businessId and userId
                    appState.navigate("${RInfoScreen.ReviewForm.name}/$businessId")
                },
                onShowReviewPageClicked = {
                    appState.navigate("${RInfoScreen.Reviews.name}/$businessId")
                },
                window = window
            ) { location ->
                appState.navigate("${RInfoScreen.Map.name}/$location")
            }
        } else {
            // Handle the case where businessId is null
        }
    }
    composable(
        route = "${RInfoScreen.Map.name}/{city}/{country}",
        arguments = listOf(navArgument("city") { type = NavType.StringType })
    ) { backStackEntry ->
        val country = backStackEntry.arguments?.getString("country")
        val city = backStackEntry.arguments?.getString("city")

        if (country != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                MapScreen(
                    onBack = {
                        appState.popUp()
                    },
                    city = city ?: "",
                    country = "Nigeria"
                )
            }
        } else {
            // Handle the case where businessId is null
        }
    }
    composable(
        route = "${RInfoScreen.ReviewForm.name}/{businessId}",
        arguments = listOf(navArgument("businessId") { type = NavType.StringType })
    ) { backStackEntry ->
        val businessId = backStackEntry.arguments?.getString("businessId")
        val userId = FirebaseAuth.getInstance().currentUser?.email

        if (businessId != null) {
            ReviewForm(
                reviewedBusinessId = businessId,
                reviewId = "",
                reviewerUserId = userId ?: "",
                onCancel = {
                    appState.popUp()
                },
                onBackPressed = {
                    appState.popUp()
                },
            )
        } else {
            // Handle the case where businessId is null
        }
    }
    composable(
        route = "${RInfoScreen.EditReviewForm.name}/{businessId}/{reviewId}",
        arguments = listOf(navArgument("businessId") { type = NavType.StringType })
    ) { backStackEntry ->
        val businessId = backStackEntry.arguments?.getString("businessId")
        val reviewId = backStackEntry.arguments?.getString("reviewId")
        val userId = FirebaseAuth.getInstance().currentUser?.email

        if (businessId != null) {
            ReviewForm(
                reviewedBusinessId = businessId,
                reviewId = reviewId ?: "",
                reviewerUserId = userId ?: "",
                onCancel = {
                    appState.popUp()
                },
                onBackPressed = {
                    appState.popUp()
                },
            )
        } else {
            // Handle the case where businessId is null
        }
    }
    composable(
        route = "${RInfoScreen.Reviews.name}/{businessId}",
        arguments = listOf(navArgument("businessId") { type = NavType.StringType })
    ) { backStackEntry ->
        val businessId = backStackEntry.arguments?.getString("businessId")
        val userId = FirebaseAuth.getInstance().currentUser?.email

        if (businessId != null) {
            ReviewsScreen(
                businessId = businessId,
                userId = userId ?: "",
                onBackButtonClick = {
                    appState.popUp()
                },
                onEditClicked = {
                    appState.navigate("${RInfoScreen.EditReviewForm.name}/$businessId/$it")
                }
            )
        } else {
            // Handle the case where businessId is null
        }
    }
    composable(route = RInfoScreen.About.name) {
        AboutScreen(
            onBackClick = {
                appState.popUp()
            },
            onNavClick = { screen ->
                appState.navigate(screen)
            }
        )
    }
    composable(route = RInfoScreen.AboutApp.name) {
        AboutAppScreen(
            onBackClick = {
                appState.popUp()
            }
        )
    }
    composable(route = RInfoScreen.TermsOfUse.name) {
        TermsOfUseScreen(
            onBackClick = {
                appState.popUp()
            }
        )
    }
    composable(route = RInfoScreen.PrivacyPolicy.name) {
        PrivacyPolicyScreen(
            onBackClick = {
                appState.popUp()
            }
        )
    }
    composable(route = RInfoScreen.ChangePassword.name) {
        ChangePasswordScreen(
            onBackClick = {
                appState.popUp()
            }
        )
    }
}
