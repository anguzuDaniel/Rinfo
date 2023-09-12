package com.danotech.rinfo.ui.screens.business.subsections

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Reviews
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.common.SnackbarManager
import com.danotech.rinfo.model.Business
import com.danotech.rinfo.ui.components.BusinessGalleryRowShimmer
import com.danotech.rinfo.ui.components.PhotoItemBitmap
import com.danotech.rinfo.ui.screens.business.BusinessViewModel
import com.danotech.rinfo.ui.screens.business.ImageItem
import com.danotech.rinfo.ui.screens.business.downloadImages
import com.google.firebase.auth.FirebaseAuth

@Composable
fun BusinessGallerySection(
    loading: Boolean,
    viewModel: BusinessViewModel,
    business: Business,
    imageList: List<ImageItem>,
    launchMultipleImages: ManagedActivityResultLauncher<String, List<Uri>>,
    onShowBusinessPhotos: () -> Unit
) {
    val context = LocalContext.current

    val defaultProfilePicture: Bitmap = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.no_image
    )

    val downloadedImages = remember { mutableStateListOf<Bitmap>() }

    LaunchedEffect(key1 = Unit) {
        downloadImages(
            businessId = business.id,
            startIndex = 0,
            onSuccess = { _, bitmap ->
                // Convert the downloaded bitmap to a Composable Painter
                downloadedImages.add(bitmap)
            },
            onError = { index, exception ->
                // Handle error for image at index
                println("Error downloading image at index $index: ${exception.message}")
            }
        )
    }

    Column(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.body_padding))
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text(
            text = "Gallery",
            style = MaterialTheme.typography.titleMedium
        )

        BusinessGalleryRowShimmer(
            isLoading = loading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (downloadedImages.isNotEmpty()) {
                BusinessGalleryRow(
                    image = downloadedImages[downloadedImages.size - 1], // Accessing the last element
                    numberOfImages = downloadedImages.size,
                    loading = loading,
                    context = context,
                    modifier = Modifier.fillMaxWidth(),
                    onShowBusinessPhotos = onShowBusinessPhotos
                )
            } else {
                BusinessGalleryRow(
                    image = defaultProfilePicture,
                    numberOfImages = 0,
                    loading = loading,
                    context = context,
                    modifier = Modifier.fillMaxWidth(),
                    onShowBusinessPhotos = onShowBusinessPhotos
                )
            }
        }


        // only user who won the current business to add images
        if (business.userId == FirebaseAuth.getInstance().currentUser?.email) {
            OutlinedButton(
                onClick = {
                    launchMultipleImages.launch("image/*")
                },
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Reviews,
                        contentDescription = "review icon",
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = "Write a review",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        if (business.userId == FirebaseAuth.getInstance().currentUser?.email) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedButton(
                    onClick = {
                        if (imageList.isNotEmpty()) {
                            val imageBitmapList = imageList.map { imageItem ->
                                imageItem.bitmap
                            }

                            // add images to the database
                            // take the current logged in users id
                            // on complete is called after successful
                            viewModel.addBusinessImages(
                                businessId = FirebaseAuth.getInstance().currentUser?.email!!,
                                imageList = imageBitmapList,
                                onComplete = {
                                    // Handle successful completion
                                    SnackbarManager.showMessage(R.string.images_uploaded_successfully)
                                },
                                onError = {
                                    // Handle error
                                    SnackbarManager.showMessage(R.string.something_went_wrong)
                                }
                            )
                        }
                    }) {
                    Text(
                        text = stringResource(id = R.string.save_change),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}


@Composable
fun BusinessGalleryRow(
    numberOfImages: Int,
    image: Bitmap,
    loading: Boolean,
    context: Context,
    onShowBusinessPhotos: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val imageSize = 80.dp

    Surface(
        color = Color.Transparent,
        modifier = modifier
            .fillMaxWidth(),
        onClick = {},
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    onShowBusinessPhotos()
                },
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            PhotoItemBitmap(
                image = image,
                size = imageSize,
                loading = loading,
                context = context
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = "Your uploads",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = if (numberOfImages > 1) "$numberOfImages images" else "$numberOfImages image",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.44f),
                )
            }
        }
    }
}

