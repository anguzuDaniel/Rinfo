package com.danotech.rinfo.ui.screens.business.subsections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.TruncateText

@Composable
fun AboutSection(
    description: String
) {
    var isShowingAllDescriptionText by remember {
        mutableStateOf(false)
    }

    val clickableText = if (isShowingAllDescriptionText) "less" else "Read more"

    Column(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.body_padding))
            .fillMaxWidth(),
    ) {
        Text(
            text = stringResource(id = R.string.description),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.ExtraBold
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (!isShowingAllDescriptionText) {
            TruncateText(
                text = description,
                maxWords = 20,  // Set the desired maximum number of words
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        } else {
            Text(
                text = description,
                style = TextStyle(
                    fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                ),
                textAlign = TextAlign.Justify,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        ClickableText(
            text = AnnotatedString(clickableText),
            style = MaterialTheme.typography.bodyMedium
                .copy(color = MaterialTheme.colorScheme.primary),
            onClick = { isShowingAllDescriptionText = !isShowingAllDescriptionText }
        )
    }
}