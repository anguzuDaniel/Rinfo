package com.danotech.rinfo.ui.screens.review

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextDecoration

@Composable
fun ExpandableReviewText(review: String) {
    var isExpanded by remember { mutableStateOf(false) }

    val maxWords = 50
    val wordsToShow = if (isExpanded) {
        review.split(" ").joinToString(" ") // Show the entire review when expanded
    } else {
        review.split(" ").take(maxWords).joinToString(" ") // Show the first 50 words
    }

    Text(
        text = AnnotatedString(wordsToShow),
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.clickable { isExpanded = !isExpanded }
    )

    if (!isExpanded && review.split(" ").size > maxWords) {
        Text(
            text = AnnotatedString("... Read More"),
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline
            ),
            modifier = Modifier.clickable { isExpanded = true }
        )
    } else if (isExpanded) {
        Text(
            text = AnnotatedString(" Read Less"),
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline
            ),
            modifier = Modifier.clickable { isExpanded = false }
        )
    }
}
