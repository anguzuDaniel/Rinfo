package com.danotech.rinfo.ui.screens.review

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danotech.rinfo.ui.screens.business.subsections.components.RatingRow
import com.danotech.rinfo.ui.screens.business.subsections.components.StarRating

@Composable
fun ReviewStatisticsScreen(
    count: Int = 0
) {
    val rating by remember { mutableStateOf(count) }
    val spaceLarge = 16.dp
    val spaceSmall = 8.dp

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Review Statistics", fontSize = 20.sp)

        Spacer(modifier = Modifier.height(spaceLarge))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = spaceSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Average Rating: ", fontWeight = FontWeight.Bold)
            StarRating(rating)
        }

        Spacer(modifier = Modifier.height(spaceLarge))

        Text(text = "Distribution of Ratings:", fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(spaceSmall))

        for (i in 5 downTo 1) {
            RatingRow(i, rating)
        }
    }
}