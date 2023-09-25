package com.danotech.rinfo.ui.screens.business

import android.net.Uri
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import com.danotech.rinfo.model.Business
import com.danotech.rinfo.model.Review
import com.danotech.rinfo.ui.screens.business.subsections.BusinessAboutSection
import com.danotech.rinfo.ui.screens.business.subsections.BusinessReviewSection
import com.danotech.rinfo.ui.screens.product.BusinessProductSection
import com.danotech.rinfo.ui.screens.review.ReviewScreenViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BusinessTabs(
    loading: Boolean,
    tabState: Int,
    viewModel: BusinessViewModel,
    imageList: List<ImageItem>,
    business: Business,
    reviewScreenViewModel: ReviewScreenViewModel,
    onAddReviewButtonClick: () -> Unit,
    onShowReviewPageClicked: () -> Unit,
    reviews: List<Review>,
    launchMultipleImages: ManagedActivityResultLauncher<String, List<Uri>>,
    onShowBusinessPhotos: () -> Unit
) {
    when (tabState) {
        0 -> BusinessAboutSection(business = business)

//            1 -> BusinessGallerySection(
//                viewModel = viewModel,
//                business = business,
//                imageList = imageList,
//                launchMultipleImages = launchMultipleImages,
//                loading = loading,
//                onShowBusinessPhotos = onShowBusinessPhotos
//            )

        1 -> BusinessProductSection(
            business = business,
        )

        2 -> BusinessReviewSection(
            business = business,
            reviewScreenViewModel = reviewScreenViewModel,
            onAllButtonReviewClick = onShowReviewPageClicked,
            onAddReviewButtonClick = onAddReviewButtonClick,
            reviews = reviews
        )

        else -> BusinessAboutSection(business = business)
    }
}