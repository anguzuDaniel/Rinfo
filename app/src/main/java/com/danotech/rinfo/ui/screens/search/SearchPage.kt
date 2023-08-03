package com.danotech.rinfo.ui.screens.search

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.SearchTextField
import com.danotech.rinfo.ui.screens.appbars.RinfoTopAppBar
import com.example.compose.AppTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchPage(

) {
    var searchQuery by remember { mutableStateOf(TextFieldValue()) }
    var searchResults by remember { mutableStateOf(emptyList<String>()) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            RinfoTopAppBar(
                title = "search",
                isShowingHomePage = false,
                isSearchPage = true,
                onBackButtonClicked = {
                    // Back button clicked
                }, actions = {
                    SearchTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = R.string.search_by_location_or_business_name,
                        modifier = Modifier,
                    )
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SearchResults(results = searchResults)
        }
    }

    // Simulate search functionality here
    LaunchedEffect(searchQuery.text) {
        // You can perform actual search operations here based on the `searchQuery` value.
        // For demonstration purposes, we are just using a simple list.
        searchResults = listOf("Result 1", "Result 2", "Result 3").filter {
            it.contains(searchQuery.text, ignoreCase = true)
        }
    }
}



@Composable
fun SearchResults(
    results: List<String>,
    modifier: Modifier = Modifier
) {
    LazyColumn {
        items(results.size) { index ->
            Text(
                text = results[index],
                modifier = modifier
                    .fillMaxWidth()
            )
            Divider()
        }
    }
}

@Preview
@Composable
fun SearchPagePreview() {
    AppTheme() {
        SearchPage()
    }
}

@Preview
@Composable
fun SearchPageDarkPreview() {
    AppTheme(
        darkTheme = true
    ) {
        SearchPage()
    }
}


