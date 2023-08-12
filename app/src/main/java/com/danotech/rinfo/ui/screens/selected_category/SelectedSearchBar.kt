package com.danotech.rinfo.ui.screens.selected_category

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.danotech.rinfo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectedSearchBar(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    viewModel: SelectedCategoryViewModel,
) {
    BackHandler() {
        onBack()
    }

    val uiState = viewModel.uiState.value

    Scaffold { innerPadding ->
        SearchBar(modifier = modifier
            .fillMaxWidth()
            .padding(paddingValues = innerPadding)
            .background(MaterialTheme.colorScheme.background),
            query = uiState.searchedCategory,
            onQueryChange = { },
            onSearch = { },
            active = false,
            onActiveChange = { },
            leadingIcon = {
                Icon(
                    modifier = Modifier.clickable { onBack() },
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.back_button)
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = stringResource(R.string.location_on)
                )
            },
            colors = SearchBarDefaults.colors(
                containerColor = MaterialTheme.colorScheme.background,
            ),
            placeholder = {
                Text(
                    text = "Hotels",
                    style = MaterialTheme.typography.labelSmall
                )
            }) {
        }
    }
}