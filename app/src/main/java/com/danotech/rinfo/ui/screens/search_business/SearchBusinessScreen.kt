package com.danotech.rinfo.ui.screens.search_business

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchPage(
    onBackPressed: () -> Unit = {},
    viewModel: SearchBusinessViewModel = hiltViewModel()
) {
    BackHandler {
        onBackPressed()
    }

    var searchQuery by remember { mutableStateOf(TextFieldValue()) }
    var searchResults by remember { mutableStateOf(emptyList<String>()) }

    val uiState = viewModel.uiState.value

    val businesses by viewModel.onSearchInput().collectAsState(initial = emptyList())

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            BusinessSearchBar(
                viewModel = viewModel,
                placeholder = stringResource(R.string.search_for_a_business),
            )
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
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        contentPadding = innerPadding
    ) {
        items(results.size) { index ->
            Text(
                text = results[index],
                modifier = modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            )
            Divider()
        }
    }
}

