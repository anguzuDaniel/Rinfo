package com.danotech.rinfo.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
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

    Box(modifier = Modifier.fillMaxSize()) {
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
                    selected = (selectedItem == navigationItemContent.label),
                    onClick = {
                        selectedItem = navigationItemContent.label
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
    bottomMenuItemsList.add(BottomMenuItem(label = "Home", icon = Icons.Filled.Home))
    bottomMenuItemsList.add(BottomMenuItem(label = "Profile", icon = Icons.Filled.Person))
    bottomMenuItemsList.add(
        BottomMenuItem(
            icon = Icons.Filled.Notifications,
            label = "notifications"
        )
    )
    bottomMenuItemsList.add(BottomMenuItem(icon = Icons.Filled.Settings, label = "notifications"))


    return bottomMenuItemsList
}


data class BottomMenuItem(
    val selected: Boolean = false,
    val icon: ImageVector,
    val label: String
)

@Preview
@Composable
fun RinfoBottomNavigationPreview() {
    AppTheme {
        RinfoBottomNavigation()
    }
}