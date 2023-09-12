@file:Suppress("KDocUnresolvedReference")

package com.danotech.rinfo.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.BottomMenuItem
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
    modifier: Modifier = Modifier,
    currentScreen: RInfoScreen,
    onTabSelected: ((RInfoScreen) -> Unit) = {},
) {
    // items list
    val bottomMenuItemsList = prepareBottomMenu()


    Box(modifier = modifier.fillMaxSize()) {
        NavigationBar(
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .clip(shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
        ) {

            bottomMenuItemsList.forEachIndexed { _, navigationItemContent ->

                NavigationBarItem(
                    selected = currentScreen == navigationItemContent.rinfoScreen,
                    onClick = {
                        onTabSelected(navigationItemContent.rinfoScreen)
                    },
                    icon = {
                        Icon(
                            imageVector =
                            if (currentScreen == navigationItemContent.rinfoScreen)
                                navigationItemContent.selectedIcon
                            else
                                navigationItemContent.unSelectedIcon,
                            contentDescription = null
                        )
                    },
                    enabled = true,
                    label = {},
                    alwaysShowLabel = false
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
            selectedIcon = Icons.Filled.Home,
            unSelectedIcon = Icons.Outlined.Home
        )
    )
    bottomMenuItemsList.add(
        BottomMenuItem(
            rinfoScreen = RInfoScreen.Search,
            selectedIcon = Icons.Filled.Search,
            unSelectedIcon = Icons.Outlined.Search,
            label = R.string.Search
        )
    )
    bottomMenuItemsList.add(
        BottomMenuItem(
            rinfoScreen = RInfoScreen.Favourites,
            label = R.string.favorites,
            selectedIcon = Icons.Filled.Bookmark,
            unSelectedIcon = Icons.Outlined.Bookmark
        )
    )
    bottomMenuItemsList.add(
        BottomMenuItem(
            rinfoScreen = RInfoScreen.Settings,
            selectedIcon = Icons.Filled.Settings,
            unSelectedIcon = Icons.Outlined.Settings,
            label = R.string.settings
        )
    )
    return bottomMenuItemsList
}


