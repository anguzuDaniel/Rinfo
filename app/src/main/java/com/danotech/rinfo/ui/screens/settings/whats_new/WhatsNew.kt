package com.danotech.rinfo.ui.screens.settings.whats_new

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.centeredTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WhatsNewPage(
    onBackClick: () -> Unit = {}
) {
    BackHandler {
        onBackClick()
    }

    var searchText by remember { mutableStateOf("") }
    val newsItems = generateNewsItems()

    val listState = rememberLazyListState()
    val hasScrolled by remember {
        derivedStateOf {
            listState.firstVisibleItemScrollOffset > 0
        }
    }
    val appBarElevation by animateDpAsState(targetValue = if (hasScrolled) 4.dp else 0.dp)

    Scaffold(
        topBar = {
            centeredTopAppBar(
                onBackClick = onBackClick,
                text = R.string.whats_new,
                hasBack = false
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(innerPadding)
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                textStyle = TextStyle(fontSize = 16.sp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        // Handle search button click
                        // You can filter news items based on the search text here
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // News List
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(newsItems) { newsItem ->
                    NewsItemCard(newsItem)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun NewsItemCard(newsItem: NewsItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = newsItem.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = newsItem.date,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = newsItem.content,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

data class NewsItem(
    val title: String,
    val date: String,
    val content: String
)

fun generateNewsItems(): List<NewsItem> {
    // Replace this with your data source or API call to fetch news items
    return listOf(
        NewsItem(
            title = "New Features Added!",
            date = "September 10, 2023",
            content = "We've added exciting new features to our app. Check them out now!"
        ),
        NewsItem(
            title = "Important Update",
            date = "August 25, 2023",
            content = "Please update your app to the latest version for better performance and security."
        ),
        // Add more news items here
    )
}
