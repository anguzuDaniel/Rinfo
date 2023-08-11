package com.danotech.rinfo.ui.screens.category

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.model.local.Category
import com.danotech.rinfo.ui.components.RinfoSearchBar
import com.danotech.rinfo.ui.theme.AppTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MoreCategoriesPage(
    viewModel: CategoryViewModel = hiltViewModel(),
    addCategoriesToDatabase: () -> Unit = {},
    onCategoryItemClicked: () -> Unit = {},
    onBackPressed: () -> Unit = {},
) {
    BackHandler {
        onBackPressed()
    }

    val uiState = viewModel.uiState.value

    val categories = viewModel.getAllCategories().collectAsState(initial = emptyList()).value

    Scaffold {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            RinfoSearchBar(
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 8.dp),
                searchInput = uiState.searchedCategory,
                onSearchInput = viewModel::onSearchInput,
                onSearch = viewModel::onSearch,
                onBack = onBackPressed,
                onClose = viewModel::onClose,
                viewModel = viewModel,
            )

            CategoriesList(
                onCategoryItemClicked = onCategoryItemClicked,
                onAddCategoryClick = viewModel::onAddCategoryClick,
                viewModel = viewModel,
                contentPadding = it,
            )
        }
    }
}


@Composable
fun CategoriesList(
    contentPadding: PaddingValues,
    onCategoryItemClicked: () -> Unit = {},
    onAddCategoryClick: () -> Unit = {},
    viewModel: CategoryViewModel,
    onCategorySearch: (String) -> Unit = {}
) {
    val categories = viewModel.getAllCategories().collectAsState(initial = emptyList()).value

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = contentPadding
    ) {
        if (viewModel.uiState.value.isLoading) {
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
        } else {
            items(categories) { category ->
                CategoriesListItem(
                    category = category,
                    onCategoryItemClicked = onCategoryItemClicked
                )
            }
        }
    }
}

@Composable
fun CategoriesListItem(
    category: Category,
    onCategoryItemClicked: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 5.dp)
            .clickable { onCategoryItemClicked() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = category.name,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            imageVector = Icons.Filled.ArrowRight,
            contentDescription = "Right Arrow",
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview
@Composable
fun MoreCategoriesPagePreview() {
    AppTheme {
        MoreCategoriesPage()
    }
}

@Preview
@Composable
fun MoreCategoriesPageDarkPreview() {
    AppTheme(
        darkTheme = true
    ) {
        MoreCategoriesPage()
    }
}
