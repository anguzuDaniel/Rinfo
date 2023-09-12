package com.danotech.rinfo.ui.screens.business

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import androidx.palette.graphics.Palette
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume

suspend fun Bitmap.computeDominantTopSectionColor(): Pair<Color, Boolean> =
    suspendCancellableCoroutine { continuation ->
        Palette.from(this)
            .setRegion(0, 0, this.width, 24.dp.value.toInt())
            .maximumColorCount(3)
            .generate { palette ->
                palette ?: continuation.cancel()

                val statusBarColorRgb = palette!!.dominantSwatch?.rgb
                statusBarColorRgb ?: continuation.cancel()

                if (statusBarColorRgb != null) {
                    val hsl = FloatArray(3)
                    ColorUtils.colorToHSL(statusBarColorRgb, hsl)
                    val isLight = hsl[2] >= 0.5
                    continuation.resume(Color(statusBarColorRgb) to isLight)
                } else {
                    // Handle the case where statusBarColorRgb is null
                    // You can provide a default color or take alternative action
                    continuation.resume(Color.Gray to false)
                }
            }
    }


/**
 * Gets images from FireStorage
 * @param businessId current business id
 * @param index the index of the images
 * @param onSuccess function called if successful
 * @param onError function called if there is an error
 */
suspend fun downloadImages(
    businessId: String,
    startIndex: Int,
    onSuccess: (Int, Bitmap) -> Unit,
    onError: (Int, Exception) -> Unit
) {
    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference

    suspend fun downloadImage(index: Int) {
        val imageName = "${businessId}_${index}.jpg"
        val imageRef = storageRef.child("business_images/${imageName}")

        try {
            val bytes = withContext(Dispatchers.IO) {
                imageRef.getBytes(Long.MAX_VALUE).await()
            }
            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            onSuccess(index, bitmap)
            // Continue downloading the next image
            downloadImage(index + 1)
        } catch (exception: Exception) {
            onError(index, exception)
        }
    }

    // Start downloading images, beginning from the specified startIndex
    downloadImage(startIndex)
}
