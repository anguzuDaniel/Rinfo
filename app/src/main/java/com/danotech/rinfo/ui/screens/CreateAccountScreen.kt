package com.danotech.rinfo.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R

@Composable
fun CreateAccountScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.body_padding))
    ) {
        Text(
            text = "Create Account",
            style = MaterialTheme.typography.titleLarge,
            modifier = modifier.padding(5.dp)
        )

        Text(
            text = "Already have an account register here",
            style = MaterialTheme.typography.bodyMedium,
            modifier = modifier.padding(5.dp)
        )

        Spacer(modifier = modifier.height(10.dp))

        TextInput(
            modifier = modifier.fillMaxWidth()
        )

        Spacer(modifier = modifier.height(10.dp))

        TextInput(
            modifier = modifier.fillMaxWidth()
        )

        Spacer(modifier = modifier.height(10.dp))

        TextInput(
            modifier = modifier.fillMaxWidth()
        )

        Spacer(modifier = modifier.height(10.dp))

        TextInput(
            modifier = modifier.fillMaxWidth()
        )

        Spacer(modifier = modifier.height(10.dp))

        RinfoButton()
    }
}

// reusable button
@Composable
fun RinfoButton(
    onClicked: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { onClicked },
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text(text = "Create Account")
    }
}

// reusable text input
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInput(
    modifier: Modifier = Modifier,
    onValueChanged: () -> Unit = {},
) {
    OutlinedTextField(
        value = "",
        onValueChange = { onValueChanged() },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            autoCorrect = false,
            imeAction = androidx.compose.ui.text.input.ImeAction.Next,
        ),
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun TextInputPreview() {
    TextInput(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun CreateAccountScreenPreview() {
    CreateAccountScreen()
}