package com.danotech.rinfo.ui.screens.business.subsections.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R

@Composable
fun SubSectionHeading(
    @StringRes text: Int
) {
    Text(
        text = stringResource(id = text),
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.onSurface.copy(0.44f),
        fontWeight = FontWeight.ExtraBold
    )
    Spacer(modifier = Modifier.height(8.dp))
}