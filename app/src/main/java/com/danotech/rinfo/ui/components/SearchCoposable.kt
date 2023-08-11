package com.danotech.rinfo.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.model.local.Category
import com.danotech.rinfo.ui.screens.category.CategoriesListItem
import com.danotech.rinfo.ui.screens.category.CategoryViewModel


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchTextField(
    icon: ImageVector = Icons.Filled.Search,
    searchInput: String = "",
    onSearchInput: (String) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 5.dp),
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
                        .padding(16.dp, vertical = 12.dp),
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
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RinfoSearchBar(
    modifier: Modifier = Modifier,
    searchInput: String = "",
    onSearchInput: (String) -> Unit = {},
    onSearch: (String) -> Unit = {},
    onBack: () -> Unit = {},
    onClose: () -> Unit = {},
    onCategoryItemClicked: () -> Unit = {},
    viewModel: CategoryViewModel,
    placeholder: String = "Search categories",
) {
    BackHandler() {
        onBack()
    }

    var active by remember {
        mutableStateOf(true)
    }

    val categories = viewModel.getAllCategories().collectAsState(initial = emptyList()).value

    val uiState = viewModel.uiState.value

    Scaffold(
        modifier = if (active) Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
        else
            modifier
                .background(MaterialTheme.colorScheme.background),
    ) { innerPadding ->
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues = innerPadding)
                .background(MaterialTheme.colorScheme.background),
            query = searchInput,
            onQueryChange = onSearchInput,
            onSearch = onSearch,
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
                        modifier = Modifier
                            .clickable {
                                if (searchInput.isNotEmpty()) {
                                    onClose()
                                } else {
                                    active = false
                                }
                            },
                        imageVector = Icons.Filled.Close,
                        contentDescription = ""
                    )
                }
            },
            colors = SearchBarDefaults.colors(
                containerColor = MaterialTheme.colorScheme.background,
            ),
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        ) {
            LazyColumn {
                if (searchInput.isEmpty()) {
                    items(categories) { category ->
                        CategoriesListItem(
                            category = category,
                            onCategoryItemClicked = onCategoryItemClicked
                        )
                    }
                } else {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                                .animateContentSize(
                                    animationSpec = (tween(
                                        durationMillis = 300,
                                        easing = LinearOutSlowInEasing
                                    ))
                                ),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
            }

        }
    }
}