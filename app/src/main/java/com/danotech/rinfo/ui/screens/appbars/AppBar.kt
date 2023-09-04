package com.danotech.rinfo.ui.screens.appbars

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RinfoTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    onBackButtonClicked: () -> Unit,
    isShowingHomePage: Boolean,
    showBackgroundColor: Boolean = true,
    isSearchPage: Boolean = false,
    actions: @Composable () -> Unit = {},
) {
    TopAppBar(
        title = if (!isShowingHomePage && !isSearchPage) {
            {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = modifier
                )
            }
        } else {
            {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        },
        navigationIcon = if (!isShowingHomePage) {
            {
                IconButton(onClick = onBackButtonClicked) {
                    /**
                     * if background color is true or shown
                     * show rounded back arrow
                     */
                    if (showBackgroundColor) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back_button)
                        )
                    } else {
                        IconButton(
                            onClick = onBackButtonClicked,
                            modifier = Modifier
                                .padding(1.dp)
                                .background(MaterialTheme.colorScheme.surface, shape = CircleShape),
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBack,
                                contentDescription = stringResource(id = R.string.back_button)
                            )
                        }
                    }
                }
            }
        } else if (isSearchPage) {
            { Box {} }
        } else {
            { Box {} }
        },
        colors = if (showBackgroundColor) {
            topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                scrolledContainerColor = MaterialTheme.colorScheme.background,
                navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                titleContentColor = MaterialTheme.colorScheme.onBackground,
                actionIconContentColor = MaterialTheme.colorScheme.onBackground
            )
        } else {
            topAppBarColors(
                containerColor = Color.Transparent,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                actionIconContentColor = MaterialTheme.colorScheme.onPrimary
            )
        },
        actions = {
            actions()
        },
        modifier = if (showBackgroundColor) {
            modifier.border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.surface,
            )
        } else {
            modifier
        },
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    )
}

// centered bottom bar layout
@Composable
fun CenteredBottomBarLayout(
    bottomBar: @Composable () -> Unit,
    fab: @Composable () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Bottom
    ) {
        Box(contentAlignment = Alignment.BottomCenter) {
            Column {
                // Your content above the BottomBar
                bottomBar()
            }
            fab()
        }
    }
}