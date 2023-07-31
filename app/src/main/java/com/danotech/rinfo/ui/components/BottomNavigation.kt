package com.danotech.rinfo.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.AppTheme

@Composable
fun RinfoBottomNavigation(
    selected: Boolean = false,
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        for (navigationItemContent in navigationItemContentList) {
            NavigationBarItem(
                selected = selected,
                onClick = { /*TODO*/ },
                icon = {
                    Icon(
                        imageVector = navigationItemContent.icon,
                        contentDescription = null
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
            )
        }
    }
}

data class NavigationItemContent(
    val selected: Boolean = false,
    val icon: ImageVector,
    val text: String
)

@Preview
@Composable
fun RinfoBottomNavigationPreview() {
    val navigationItemContentList = listOf<NavigationItemContent>(
        NavigationItemContent(
            selected = true,
            icon = Icons.Default.Home,
            text = "Home"
        ),
        NavigationItemContent(
            icon = Icons.Default.Favorite,
            text = "favorite"
        ),
        NavigationItemContent(
            icon = Icons.Default.Notifications,
            text = "notifications"
        ),
        NavigationItemContent(
            icon = Icons.Default.Settings,
            text = "notifications"
        )
    )
    AppTheme {
        RinfoBottomNavigation(
            navigationItemContentList = navigationItemContentList,
        )
    }
}