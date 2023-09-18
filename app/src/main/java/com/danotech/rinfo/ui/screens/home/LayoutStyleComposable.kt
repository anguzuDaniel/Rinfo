package com.danotech.rinfo.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun ChangeLayoutAction() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        ChangeLayoutIconItem(
            icon = Icons.AutoMirrored.Filled.ListAlt,
            onClick = {}
        )

        ChangeLayoutIconItem(
            icon = Icons.Filled.FilterAlt,
            onClick = {}
        )

        ChangeLayoutIconItem(
            icon = Icons.AutoMirrored.Filled.ViewList,
            onClick = {}
        )
    }
}

@Composable
private fun ChangeLayoutIconItem(
    icon: ImageVector,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "show all",
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}