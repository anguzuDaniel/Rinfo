package com.danotech.rinfo.ui.screens.notification

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import com.danotech.rinfo.ui.screens.Home.CenteredBottomBarLayout
import com.example.compose.AppTheme


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationPage() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            CenteredBottomBarLayout(
                bottomBar = { RinfoBottomNavigation() },
                fab = {
                    FloatingActionButton(
                        onClick = {
                            // FAB onClick
                        },
                        modifier = Modifier.padding(bottom = 10.dp),
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(id = R.string.search)
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            NotificationList()
        }
    }
}

@Composable
fun NotificationList() {
    LazyColumn {
        items(10) { index ->
            NotificationItem(index)
            Divider()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationItem(index: Int) {
    ListItem(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        headlineText = { Text("Notification $index") },
        supportingText = { Text("This is a sample notification message.") },
        trailingContent = {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                tint = Color.Red
            )
        }
    )
}

@Preview
@Composable
fun PreviewNotificationPage() {
    NotificationPage()
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
            text = "Notification Example",
            style = MaterialTheme.typography.titleSmall
        )
    }
}

data class Notifications(
    val title: String,
    val text: String,
    val time: String,
    val icon: Int
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