package com.danotech.rinfo.ui.screens.business.subsections

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.danotech.rinfo.R
import com.danotech.rinfo.common.SnackbarManager
import com.danotech.rinfo.model.Business
import com.danotech.rinfo.ui.screens.business.BusinessViewModel
import com.danotech.rinfo.ui.screens.business.ImageItem
import com.danotech.rinfo.ui.screens.business.downloadImages
import com.google.firebase.auth.FirebaseAuth

@Composable
fun BusinessGallerySection(
    viewModel: BusinessViewModel,
    business: Business,
    imageList: List<ImageItem>
) {
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
            .fillMaxWidth(),
    ) {
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