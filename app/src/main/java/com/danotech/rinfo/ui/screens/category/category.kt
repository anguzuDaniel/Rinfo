package com.danotech.rinfo.ui.screens.category

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.data.LocalReviewProvider
import com.danotech.rinfo.model.local.Category
import com.danotech.rinfo.ui.screens.appbars.RinfoTopAppBar
import com.danotech.rinfo.ui.theme.AppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreCategoriesPage(
    viewModel: CategoryViewModel = hiltViewModel(),
    addCategoriesToDatabase: () -> Unit = {},
    onCategoryItemClicked: () -> Unit = {},
    onBackPressed: () -> Unit = {}
) {
    BackHandler {
        onBackPressed()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            RinfoTopAppBar(
                title = stringResource(id = R.string.category),
                isShowingHomePage = false,
                onBackButtonClicked = {
                    onBackPressed()
                },
            )
        },
    ) { innerPadding ->
        CategoriesList(
            innerPadding = innerPadding,
            categories = LocalReviewProvider.categories,
            onCategoryItemClicked = onCategoryItemClicked,
            onAddCategoryClick = viewModel::onAddCategoryClick
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchTextField() {
    var searchQuery by remember { mutableStateOf(TextFieldValue()) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val searchIcon = Icons.Filled.Search

    Surface(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 5.dp),
    ) {

        BasicTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
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
                        imageVector = searchIcon,
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

@Composable
fun CategoriesList(
    innerPadding: PaddingValues,
    onCategoryItemClicked: () -> Unit = {},
    categories: List<Category>,
    onAddCategoryClick: () -> Unit = {}
) {
    LazyColumn(
        contentPadding = innerPadding,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {


        item {
            SearchTextField()
        }

        item {
            Button(onClick = onAddCategoryClick) {
                Text(text = "Add categories to database")
            }
        }

        items(categories) { category ->
            CategoriesListItem(
                category = category,
                onCategoryItemClicked = onCategoryItemClicked
            )
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
