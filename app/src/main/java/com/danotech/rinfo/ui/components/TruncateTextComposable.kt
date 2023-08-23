package com.danotech.rinfo.ui.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

/**
 * used the truncate the text
 * reduce the number of words shown using the maxWords parameter
 */
@Composable
fun TruncateText(
    text: String,
    maxWords: Int,
    style: TextStyle,
    color: Color
) {
    val words = text.split(" ")
    val truncatedText = if (words.size <= maxWords) {
        text
    } else {
        words.subList(0, maxWords).joinToString(" ")
    }
    Text(
        text = truncatedText,
        style = style,
        color = color
    )
}