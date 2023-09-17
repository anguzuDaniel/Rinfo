package com.danotech.rinfo.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R


@Composable
fun SignUpButton(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    enabled: Boolean,
    action: () -> Unit, // DON'T REMOVE: should always be the last
) {
    RinfoButton(
        name = R.string.sign_in,
        isLoading = isLoading,
        enabled = enabled,
        onClicked = action,
        modifier = modifier
    )
}

@Composable
fun SignInButton(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    enabled: Boolean,
    action: () -> Unit, // DON'T REMOVE: should always be the last
) {
    RinfoButton(
        name = R.string.sign_in,
        isLoading = isLoading,
        enabled = enabled,
        onClicked = action,
        modifier = modifier
    )
}

@Composable
fun BusinessAccountButton(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    action: () -> Unit,
) {
    ButtonWithLoader(isLoading = isLoading, modifier = modifier, action = action)
}

@Composable
fun ProfileButton(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    action: () -> Unit,
) {
    ButtonWithLoader(isLoading = isLoading, modifier = modifier, action = action)
}

@Composable
private fun ButtonWithLoader(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    action: () -> Unit,
) {
    Button(
        onClick = action,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = modifier
                .animateContentSize(
                    animationSpec = (tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    ))
                )
                .background(MaterialTheme.colorScheme.primary),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = if (isLoading) "Saving" else "Save",
                color = Color.White,
            )
            if (isLoading) {
                Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

/**
 * reusable button
 * provide a string resource for the button name
 * provide a click handler
 */
@Composable
fun RinfoButton(
    modifier: Modifier = Modifier,
    @StringRes name: Int,
    onClicked: () -> Unit = {},
    isLoading: Boolean = false,
    enabled: Boolean = false
) {
    Button(
        onClick = onClicked,
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        shape = MaterialTheme.shapes.small,
        enabled = enabled
    ) {
        if (!isLoading) {
            Text(
                text = stringResource(id = name),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        } else {
            CircularProgressIndicator(
                modifier = Modifier.size(15.dp),
                strokeWidth = 2.dp,
                color = Color.White
            )
        }
    }
}

/**
 * reusable text input
 */
@Composable
fun CategoryIconButton(
    modifier: Modifier = Modifier,
    description: String,
    @DrawableRes icon: Int,
    @StringRes name: Int,
    onCategoryClicked: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(shape = MaterialTheme.shapes.medium)
        ) {
            IconButton(
                onClick = onCategoryClicked,
                modifier = modifier
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    contentDescription = description
                )
            }
        }

//        Spacer(modifier = Modifier.padding(5.dp))
//
//        Text(
//            text = stringResource(id = name),
//            style = MaterialTheme.typography.labelSmall,
//            color = MaterialTheme.colorScheme.onSurface,
//        )
    }
}

/**
 * reusable text input
 * show options for the search screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBusinessButton(
    name: String,
    active: Boolean = false,
    onFilterClick: () -> Unit = {}
) {
    if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface

    var selected by remember { mutableStateOf(false) }
    FilterChip(
        selected = selected,
        onClick = {
            selected = !selected
            onFilterClick()
        },
        label = { Text(text = name) },
        leadingIcon = if (selected) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Localized Description",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else {
            {}
        }
    )
}