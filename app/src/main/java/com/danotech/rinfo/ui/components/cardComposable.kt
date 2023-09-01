package com.danotech.rinfo.ui.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.model.Business
import com.google.firebase.storage.FirebaseStorage

/**
 * Review card
 * when clicked it redirects you to the review page
 * @param business
 * @param onReviewCardClicked takes an int which is the currents review's id
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewCard(
    business: Business,
    modifier: Modifier = Modifier,
    onReviewCardClicked: (Business) -> Unit = {}
) {
    val context = LocalContext.current

    val defaultProfilePicture: Bitmap = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.no_image
    )

    val bitmap = remember {
        mutableStateOf(defaultProfilePicture)
    }

    getImage(
        businessId = business.id,
        bitmap = bitmap
    )

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
        ),
        onClick = { onReviewCardClicked(business) },
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement
                .spacedBy(dimensionResource(id = R.dimen.spacer_medium))
        ) {
            Column(
                modifier = Modifier
                    .size(100.dp),
            ) {
                Image(
                    bitmap = bitmap.value.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .aspectRatio(1f)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                )
            }

            Column {
                Text(
                    text = business.name,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                TruncateText(
                    text = business.description,
                    maxWords = 10,  // Set the desired maximum number of words
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Spacer(modifier = Modifier.height(4.dp))

                RatingStars(
                    rating = business.reviews,
                )
            }
        }
    }
}

private fun getImage(
    businessId: String,
    bitmap: MutableState<Bitmap>
) {
    // Inside your function
    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference

    val imageName = "${businessId}.jpg"
    // Replace with the actual image name
    val imageRef = storageRef.child("logos/${imageName}")

    imageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener { bytes ->
        // Successfully retrieved image bytes
        val image = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        // Use the bitmap as needed (e.g., display in ImageView)

        bitmap.value = image
    }.addOnFailureListener {
        // Handle failure
    }
}