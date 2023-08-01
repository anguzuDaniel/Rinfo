package com.danotech.rinfo.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

// reusable text input
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInput(
    leadingIcon: ImageVector? = null,
    labelText: String,
    modifier: Modifier = Modifier,
    onValueChanged: () -> Unit = {},
) {
    OutlinedTextField(
        value = "",
        onValueChange = { onValueChanged },
        leadingIcon = {
            Icon(
                imageVector = leadingIcon!!,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = null
            )
        },
        label = {
            Text(
                text = labelText,
                style = MaterialTheme.typography.bodyMedium,
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            autoCorrect = false,
            imeAction = ImeAction.Next,
        ),
        modifier = modifier.fillMaxWidth(),
    )
    Spacer(modifier = modifier.height(5.dp))
}