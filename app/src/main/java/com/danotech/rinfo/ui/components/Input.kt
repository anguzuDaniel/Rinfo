package com.danotech.rinfo.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.theme.AppTheme

// reusable text input
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInput(
    leadingIcon: ImageVector? = null,
    labelText: String,
    modifier: Modifier = Modifier,
    onValueChanged: () -> Unit = {},
    onSearchInputClicked: () -> Unit = {},
) {
    OutlinedTextField(
        value = "",
        onValueChange = { onValueChanged() },
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
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = { onSearchInputClicked() }),
    )
    Spacer(modifier = modifier.height(5.dp))
}


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    onSearchInputClicked: () -> Unit = {},
    @StringRes placeholder: Int,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val searchIcon = Icons.Default.Search

    Column(
        modifier = modifier
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = MaterialTheme.typography.labelSmall,
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(R.string.search),
                    modifier = Modifier.size(24.dp)
                )
            },
            placeholder = {
                Text(
                    text = stringResource(placeholder),
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(start = 8.dp)
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { keyboardController?.hide() }),
            modifier = modifier.clickable(onClick = onSearchInputClicked)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextInputPreview() {
    AppTheme(
        darkTheme = true
    ) {
        TextInput(
            labelText = "Name",
            leadingIcon = Icons.Filled.Person
        )
    }
}