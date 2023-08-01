package com.danotech.rinfo.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.danotech.rinfo.R
import com.example.compose.AppTheme

@Composable
fun RinfoBottomNavigation(
    modifier: Modifier = Modifier
) {

    // items list
    val bottomMenuItemsList = prepareBottomMenu()

    val contextForToast = LocalContext.current.applicationContext

    var selectedItem by remember {
        mutableStateOf("Home")
    }

    Box(modifier = modifier.fillMaxSize()) {
        BottomAppBar(
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
                    selected = (selectedItem == navigationItemContent.label.toString()),
                    onClick = {
                        selectedItem = navigationItemContent.label.toString()
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

public fun prepareBottomMenu(): List<BottomMenuItem> {
    val bottomMenuItemsList = arrayListOf<BottomMenuItem>()

    // add menu items
    bottomMenuItemsList.add(
        BottomMenuItem(
            label = R.string.home,
            icon = Icons.Filled.Home
        )
    )
    bottomMenuItemsList.add(
        BottomMenuItem(
            label = R.string.add_to_favorite,
            icon = Icons.Filled.Favorite
        )
    )
    bottomMenuItemsList.add(
        BottomMenuItem(
            icon = Icons.Filled.Notifications,
            label = R.string.notifications
        )
    )
    bottomMenuItemsList.add(BottomMenuItem(icon = Icons.Filled.Settings, label = R.string.settings))


    return bottomMenuItemsList
}


data class BottomMenuItem(
    val selected: Boolean = false,
    val icon: ImageVector,
    @StringRes val label: Int
)

@Preview
@Composable
fun RinfoBottomNavigationPreview() {
    AppTheme {
        RinfoBottomNavigation()
    }
}