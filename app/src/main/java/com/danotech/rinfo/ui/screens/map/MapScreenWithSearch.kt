package com.danotech.rinfo.ui.screens.map

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOff
import androidx.compose.material.icons.filled.LocationOn
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.Loading

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreenWithSearch(
    city: String,
    country: String,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onClose: () -> Unit = {},
    viewModel: MapViewModel = hiltViewModel(),
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

    Scaffold { innerPadding ->
        SearchBar(modifier = modifier
            .fillMaxWidth()
            .padding(paddingValues = innerPadding)
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
                Icon(
                    modifier = Modifier.clickable { onClose() },
                    imageVector = if (uiState.isFalloutMap) Icons.Filled.LocationOff else Icons.Filled.LocationOn,
                    contentDescription = stringResource(id = R.string.location_on)
                )
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
                MapDisplay(
                    viewModel = viewModel,
                    country = country,
                    city = city
                )
            }
        }
    }
}