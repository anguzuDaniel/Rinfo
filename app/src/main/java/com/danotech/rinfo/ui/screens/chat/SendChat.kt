@file:OptIn(ExperimentalMaterial3Api::class)

package com.danotech.rinfo.ui.screens.chat

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.centeredTopAppBar

@Composable
fun ConversationScreen(
    onBackClick: () -> Unit = {},
) {
    BackHandler {
        onBackClick()
    }

    rememberLazyListState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            centeredTopAppBar(
                onBackClick = onBackClick,
                text = R.string.conversation
            )
        },
        bottomBar = {
            ConversationBottom()
        }
    ) { innerPadding ->
        MessageList(innerPadding = innerPadding)
    }
}

@Composable
fun ConversationBottom() {
    Column(
        modifier = Modifier.padding(10.dp)
    ) {
        SearchBar(
            query = "",
            placeholder = {
                Text(
                    text = "Message",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.44f)
                )
            },
            onQueryChange = {},
            onSearch = {},
            active = false,
            onActiveChange = {
            }) {

        }
    }
}

@Composable
fun MessageList(
    innerPadding: PaddingValues
) {
    LazyColumn(
        contentPadding = innerPadding,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize()
    ) {
        item {

        }
    }
}

