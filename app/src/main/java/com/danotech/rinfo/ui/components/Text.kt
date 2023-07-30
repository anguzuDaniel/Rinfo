package com.danotech.rinfo.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun HeadingText(
    @StringRes text: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(id = text),
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = modifier.padding(5.dp)
    )
}

@Composable
fun SubHeadingText(
    @StringRes text: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(id = text),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = modifier.padding(5.dp)
    )
}
