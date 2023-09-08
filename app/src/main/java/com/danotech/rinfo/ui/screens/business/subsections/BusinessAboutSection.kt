package com.danotech.rinfo.ui.screens.business.subsections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.danotech.rinfo.R
import com.danotech.rinfo.model.Business
import com.danotech.rinfo.ui.components.SubHeadingText
import com.danotech.rinfo.ui.components.TruncateText

@Composable
fun BusinessAboutSection(
    business: Business
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
        SubHeadingText(text = R.string.description)

        if (!isShowingAllDescriptionText) {
            TruncateText(
                text = business.description,
                maxWords = 20,  // Set the desired maximum number of words
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        } else {
            Text(
                text = business.description,
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