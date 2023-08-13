package com.danotech.rinfo.ui.screens.notification

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.RinfoBottomNavigation
import com.danotech.rinfo.ui.screens.RInfoScreen
import com.danotech.rinfo.ui.screens.appbars.CenteredBottomBarLayout
import com.danotech.rinfo.ui.screens.appbars.RinfoTopAppBar
import com.danotech.rinfo.ui.theme.AppTheme


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NotificationPage(
    onBackPressed: () -> Unit = {},
    onTabSelected: (RInfoScreen) -> Unit = {},
) {
    BackHandler {
        onBackPressed()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            RinfoTopAppBar(
                title = stringResource(id = R.string.notifications),
                isShowingHomePage = false,
                onBackButtonClicked = onBackPressed,
                modifier = Modifier.background(MaterialTheme.colorScheme.background)
            )
        },
        bottomBar = {
            CenteredBottomBarLayout(bottomBar = {
                RinfoBottomNavigation(
                    currentScreen = RInfoScreen.Notification,
                    onTabSelected = onTabSelected,
                    modifier = Modifier.fillMaxWidth()
                )
            }, fab = {
//                FloatingActionButton(
//                    onClick = onFabClicked,
//                    modifier = Modifier.padding(bottom = 10.dp),
//                    contentColor = MaterialTheme.colorScheme.onPrimary,
//                    containerColor = MaterialTheme.colorScheme.primary
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Search,
//                        contentDescription = stringResource(id = R.string.search)
//                    )
//                }
            })
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            NotificationList(
                innerPadding = innerPadding
            )
        }
    }
}


@Composable
fun NotificationList(
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = innerPadding,
        modifier = modifier
    ) {
        items(10) { index ->
            NotificationItem(index)
            Divider()
        }
    }
}

@Composable
fun NotificationItem(index: Int) {
    ListItem(modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = { }),
        headlineContent = { Text("Notification $index") },
        supportingContent = { Text("This is a sample notification message.") },
        trailingContent = {
            Icon(
                imageVector = Icons.Default.Favorite, contentDescription = null, tint = Color.Red
            )
        })
}

@Composable
fun NotificationCard(notification: Notifications) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = null,
            modifier = Modifier.size(100.dp),
            tint = Color.Red
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Notification Example", style = MaterialTheme.typography.titleSmall
        )
    }
}

data class Notifications(
    val title: String, val text: String, val time: String, val icon: Int
)

@Preview(showBackground = true)
@Composable
fun NotificationCardPreview() {
    AppTheme(
        darkTheme = false
    ) {
        NotificationPage()
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationCardDarkPreview() {
    AppTheme(
        darkTheme = true
    ) {
        NotificationPage()
    }
}