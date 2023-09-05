package com.danotech.rinfo.ui.screens.search_business

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.model.Business

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchPage(
    onBackPressed: () -> Unit = {},
    viewModel: SearchBusinessViewModel = hiltViewModel()
) {
    BackHandler {
        onBackPressed()
    }


    BusinessSearchBar(
        viewModel = viewModel,
        onBack = onBackPressed,
    )
}


@Composable
fun SearchResults(
    results: List<Business>,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        contentPadding = innerPadding
    ) {
        items(results, key = { r -> r.id }) { review ->
            Text(
                text = review.name,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            )
            HorizontalDivider()
        }
    }
}

