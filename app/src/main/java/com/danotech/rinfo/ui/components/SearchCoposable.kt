package com.danotech.rinfo.ui.components

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.screens.category.CategoriesListItem
import com.danotech.rinfo.ui.screens.category.CategoryViewModel
import com.danotech.rinfo.ui.theme.AppTheme


@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Filled.Search,
    searchInput: String = "",
    onSearchInput: (String) -> Unit = {},
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(
        modifier = modifier
    ) {
        BasicTextField(
            value = searchInput,
            onValueChange = onSearchInput,
            textStyle = MaterialTheme.typography.labelSmall,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { keyboardController?.hide() }),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onSurface,
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(horizontal = 8.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Search Icon",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(modifier = Modifier.weight(1f)) {
                        innerTextField()
                        Text(text = "Search", style = MaterialTheme.typography.labelSmall)
                    }
                }
            },
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySearchBar(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onClose: () -> Unit = {},
    navigateToCategoryPage: () -> Unit = {},
    viewModel: CategoryViewModel,
    placeholder: String = "Search categories",
) {
    BackHandler {
        onBack()
    }

    val uiState = viewModel.uiState.value

    var active by remember {
        mutableStateOf(true)
    }

    val categorySearchResults by viewModel.getCategoryByName().collectAsState(initial = emptyList())

    Scaffold(
        modifier = if (active) Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
        else modifier.background(MaterialTheme.colorScheme.background),
    ) {
        SearchBar(modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
            query = uiState.searchedCategory,
            onQueryChange = viewModel::onSearchInput,
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
                            if (uiState.searchInput.isNotEmpty()) {
                                onClose()
                            } else {
                                active = false
                            }
                        },
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(R.string.close)
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
                items(categorySearchResults, key = { cat -> cat.id }) { category ->
                    CategoriesListItem(
                        category = category,
                        onCategoryItemClicked = navigateToCategoryPage
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun SearchPreview() {
    AppTheme {
        SearchTextField()
    }
}