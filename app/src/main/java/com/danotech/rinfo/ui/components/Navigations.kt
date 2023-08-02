package com.danotech.rinfo.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.BottomMenuItem
import com.danotech.rinfo.ui.RinfoAppUiState
import com.danotech.rinfo.ui.screens.RInfoScreen

/**
 * Bottom menu item
 * @param rinfoScreen
 * @param label
 * @param icon
 * @param enabled
 * @constructor Create empty Bottom menu item
 * @property rinfoScreen
 * @property label
 * @property icon
 * @property enabled
 * @property onClick
 * @property selected
 * @property modifier
 * @property contentColor
 * @property contentDescription
 * @property tint
 * @property onClick
 * @property selected
 * @property modifier
 * @property contentColor
 *
 * uses the bottom menu items created in the prepareBottomMenu() function
 * loops through the items and adds them to the bottom menu
 *
 * takes the current screen and sets it to the selected item
 *
 */
@Composable
fun RinfoBottomNavigation(
    rinfoAppUiState: RinfoAppUiState,
    currentScreen: RInfoScreen,
    onTabSelected: ((RInfoScreen) -> Unit) = {},
    modifier: Modifier = Modifier,
) {

    // items list
    val bottomMenuItemsList = prepareBottomMenu()

    val contextForToast = LocalContext.current.applicationContext

    var selectedItem by remember {
        mutableStateOf("Home")
    }

    Box(modifier = modifier.fillMaxSize()) {
        NavigationBar(
            modifier = Modifier.align(alignment = Alignment.BottomCenter)
        ) {

            bottomMenuItemsList.forEachIndexed { index, navigationItemContent ->
                if (index == 2) {
                    // add an empty space for FAB
                    NavigationBarItem(
                        selected = false,
                        onClick = {},
                        icon = {},
                        enabled = false
                    )
                }

                NavigationBarItem(
                    selected = currentScreen == navigationItemContent.rinfoScreen,
                    onClick = {
                        onTabSelected(navigationItemContent.rinfoScreen)
                    },
                    icon = {
                        Icon(
                            imageVector = navigationItemContent.icon,
                            contentDescription = null
                        )
                    },
                    enabled = true,
                )
            }
        }
    }
}

/**
 * Used to prepare the bottom menu
 * @return
 *  returns a list of bottom menu items
 *  adds the menu items to the list
 *  returns the list
 * @constructor Create empty Prepare bottom menu
 */
private fun prepareBottomMenu(): List<BottomMenuItem> {
    val bottomMenuItemsList = arrayListOf<BottomMenuItem>()

    // add menu items
    bottomMenuItemsList.add(
        BottomMenuItem(
            rinfoScreen = RInfoScreen.Home,
            label = R.string.home,
            icon = Icons.Filled.Home
        )
    )
    bottomMenuItemsList.add(
        BottomMenuItem(
            rinfoScreen = RInfoScreen.Favourites,
            label = R.string.favorites,
            icon = Icons.Filled.Favorite
        )
    )
    bottomMenuItemsList.add(
        BottomMenuItem(
            rinfoScreen = RInfoScreen.Notification,
            icon = Icons.Filled.Notifications,
            label = R.string.notifications
        )
    )
    bottomMenuItemsList.add(
        BottomMenuItem(
            rinfoScreen = RInfoScreen.Settings,
            icon = Icons.Filled.Settings,
            label = R.string.settings
        )
    )
    return bottomMenuItemsList
}


