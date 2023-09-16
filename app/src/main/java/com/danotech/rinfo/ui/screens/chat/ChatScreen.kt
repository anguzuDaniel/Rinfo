@file:OptIn(ExperimentalMaterial3Api::class)

package com.danotech.rinfo.ui.screens.chat

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.helpers.timeAgo
import com.danotech.rinfo.ui.components.NoDataScreen
import com.danotech.rinfo.ui.components.centeredTopAppBar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatScreen(
    onBackClick: () -> Unit = {},
    onChatClick: () -> Unit = {}
) {
    BackHandler {
        onBackClick()
    }

    val listState = rememberLazyListState()
    val hasScrolled by remember {
        derivedStateOf {
            listState.firstVisibleItemScrollOffset > 0
        }
    }
    val appBarElevation by animateDpAsState(targetValue = if (hasScrolled) 4.dp else 0.dp)

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    // val storedDateTimeString = "2023-08-13 10:00:00" // Not needed
    val storedDateTime = LocalDateTime.parse("2023-08-13 10:00:00", formatter)
    val currentDateTime = LocalDateTime.now()

    val timeDifference = timeAgo(storedDateTime, currentDateTime)

    val chats = listOf(
        Chat(
            name = "Daniel",
            message = "Hello there",
            time = timeDifference
        ),
        Chat(
            name = "John",
            message = "Hello there, Where can i find your business?",
            time = timeDifference
        ),
        Chat(
            name = "Suzan",
            message = "Love your business.",
            time = timeDifference
        ),
        Chat(
            name = "Catherine",
            message = "Big fun of your business..",
            time = timeDifference
        ),
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            centeredTopAppBar(
                onBackClick = onBackClick,
                text = R.string.chats
            )
        },
    ) { innerPadding ->
        if (chats.isEmpty()) {
            NoDataScreen(
                text = R.string.no_chats,
                modifier = Modifier.padding(innerPadding)
            )
        } else {
            ChatList(
                chats = chats,
                innerPadding = innerPadding,
                onChatClick = onChatClick
            )
        }
    }
}