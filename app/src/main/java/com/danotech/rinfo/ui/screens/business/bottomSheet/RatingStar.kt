package com.danotech.rinfo.ui.screens.business.bottomSheet

import android.content.res.ColorStateList
import android.graphics.BlendMode
import android.os.Build
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun RatingInputRow(
    rating: Int,
    onRatingChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    AndroidView(
        factory = { context ->
            RatingBar(context).apply {
                stepSize = .5f
            }
        },
        update = { ratingBar ->
            ratingBar.rating = rating.toFloat()
            ratingBar.numStars = 5
            ratingBar.progressTintList = ColorStateList.valueOf(Color(0xFFB42625).toArgb())
            ratingBar.progressTintBlendMode = BlendMode.DIFFERENCE
            ratingBar.setOnRatingBarChangeListener { _, _, _ ->
                onRatingChange(ratingBar.rating.toInt())
            }
            ratingBar.progressTintMode = android.graphics.PorterDuff.Mode.SRC_ATOP
        },
    )
}

@Composable
fun InputRow(
    inputLabel: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Row(
        modifier = modifier.padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = inputLabel,
            fontWeight = FontWeight.SemiBold,
            modifier = modifier
                .weight(1f)
                .padding(end = 8.dp),
        )
        Box(modifier = Modifier.weight(2f)) {
            content()
        }
    }
}