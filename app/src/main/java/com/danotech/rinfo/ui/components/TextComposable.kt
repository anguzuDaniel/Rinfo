package com.danotech.rinfo.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
    @StringRes text: Int
) {
    Text(
        text = stringResource(id = text),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface,
        fontSize = 20.sp,
    )
}

@Composable
fun ClickableTextRow(
    @StringRes noneClickableText: Int,
    @StringRes clickableText: Int,
    modifier: Modifier = Modifier,
    onSignUpTextClicked: () -> Unit = { }
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(id = noneClickableText),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.width(16.dp))

        ClickableText(
            text = AnnotatedString(text = stringResource(id = clickableText)),
            onClick = { onSignUpTextClicked() },
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
}
