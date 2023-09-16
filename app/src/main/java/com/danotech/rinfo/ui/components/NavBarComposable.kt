@file:Suppress("KDocUnresolvedReference")
@file:OptIn(ExperimentalMaterial3Api::class)

package com.danotech.rinfo.ui.components

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        NavigationBar(
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter),
//                .clip(shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            tonalElevation = 20.dp,
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.background
        ) {
            bottomMenuItemsList.forEachIndexed { _, navigationItemContent ->
                NavigationBarItem(
                    selected = currentScreen == navigationItemContent.rinfoScreen,
                    onClick = {
                        onTabSelected(navigationItemContent.rinfoScreen)
                    },
                    icon = {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(3.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector =
                                if (currentScreen == navigationItemContent.rinfoScreen)
                                    navigationItemContent.selectedIcon
                                else
                                    navigationItemContent.unSelectedIcon,
                                contentDescription = null
                            )

                            AnimatedVisibility(currentScreen == navigationItemContent.rinfoScreen) {
                                Box(
                                    modifier = Modifier
                                        .size(5.dp)
                                        .background(
                                            MaterialTheme.colorScheme.onSurfaceVariant,
                                            CircleShape
                                        )
                                )
                            }
                        }
                    },
                    enabled = true,
                    alwaysShowLabel = false,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        indicatorColor = MaterialTheme.colorScheme.surfaceVariant,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.50f)
                    )
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
            rinfoScreen = RInfoScreen.Notification,
            label = R.string.notifications,
            selectedIcon = Icons.Filled.Notifications,
            unSelectedIcon = Icons.Outlined.Notifications
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

/**
 * @param hasBack show back button if true
 * @param onBackClick called when back button is clicked
 * @param text a string resource id representing the text you want to be shown
 */
@Composable
fun centeredTopAppBar(
    hasBack: Boolean = true,
    onBackClick: () -> Unit = {},
    @StringRes text: Int
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    val listState = rememberLazyListState()
    val hasScrolled by remember {
        derivedStateOf {
            listState.firstVisibleItemScrollOffset > 0
        }
    }

    rememberTopAppBarState()
    val appBarElevation by animateDpAsState(targetValue = if (hasScrolled) 20.dp else 0.dp)
    val borderSize = if (hasScrolled) 5.dp else 0.dp
    val borderColor =
        if (hasScrolled) MaterialTheme.colorScheme.onBackground.copy(0.44f) else Color.Transparent

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier
            .shadow(appBarElevation)
            .border(borderSize, borderColor),
        title = {
            Text(
                text = stringResource(id = text),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
            )
        },
        navigationIcon = {
            if (hasBack) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_button)
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior
    )
}


