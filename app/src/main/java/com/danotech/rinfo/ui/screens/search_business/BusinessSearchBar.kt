package com.danotech.rinfo.ui.screens.search_business

import android.annotation.SuppressLint
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.Loading

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusinessSearchBar(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onClose: () -> Unit = {},
    viewModel: SearchBusinessViewModel,
) {
    BackHandler {
        onBack()
    }

    var active by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(viewModel) {
        viewModel.searchBusinesses()
    }

    val uiState = viewModel.uiState.collectAsState().value

    Scaffold {  _ ->
        SearchBar(modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
            query = uiState.query,
            onQueryChange = viewModel::onQueryChanged,
            onSearch = viewModel::onSearch,
            active = active,
            onActiveChange = { active = it },
            leadingIcon = {
                Icon(
                    modifier = Modifier.clickable { onBack() },
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.back_button)
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
                        }, imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(id = R.string.close)
                    )
                }
            },
            colors = SearchBarDefaults.colors(
                containerColor = MaterialTheme.colorScheme.background,
            ),
            placeholder = {
                Text(
                    text = stringResource(R.string.search_for_a_business),
                    style = MaterialTheme.typography.labelSmall
                )
            }) {
            if (uiState.isLoading) {
                Loading()
            } else {
                if (uiState.businesses.isEmpty() && uiState.query.isNotEmpty()) {
                    Text(
                        text = stringResource(R.string.no_businesses_found),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    LazyColumn {
                        items(uiState.businesses, key = { b -> b.id }) { business ->
                            BusinessListItem(
                                business = business,
                                onCategoryItemClicked = {}
                            )
                        }
                    }
                }
            }
        }
    }
}