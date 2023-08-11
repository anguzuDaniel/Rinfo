package com.danotech.rinfo.ui.screens.search_business

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusinessSearchBar(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onClose: () -> Unit = {},
    viewModel: SearchBusinessViewModel,
    placeholder: String = "Search categories",
) {
    BackHandler() {
        onBack()
    }

    var active by remember {
        mutableStateOf(true)
    }

    val uiState = viewModel.uiState.value

    val businesses = viewModel.uiState.value.businesses

    Scaffold { innerPadding ->
        SearchBar(modifier = modifier
            .fillMaxWidth()
            .padding(paddingValues = innerPadding)
            .background(MaterialTheme.colorScheme.background),
            query = uiState.query,
            onQueryChange = {
                uiState.query = it
            },
            onSearch = {
                uiState.query = it
            },
            active = active,
            onActiveChange = { active = it },
            leadingIcon = {
                Icon(
                    modifier = Modifier.clickable { onBack() },
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = ""
                )
            },
            trailingIcon = {
                if (active) {
                    Icon(
                        modifier = Modifier.clickable {
                            if (uiState.query.isNotEmpty()) {
                                onClose()
                            } else {
                                active = false
                            }
                        }, imageVector = Icons.Filled.Close, contentDescription = ""
                    )
                }
            },
            colors = SearchBarDefaults.colors(
                containerColor = MaterialTheme.colorScheme.background,
            ),
            placeholder = {
                Text(
                    text = placeholder, style = MaterialTheme.typography.labelSmall
                )
            }) {
            LazyColumn {
                items(businesses) { business ->
                    BusinessListItem(
                        business = business,
                        onCategoryItemClicked = {}
                    )
                }
            }
        }
    }
}