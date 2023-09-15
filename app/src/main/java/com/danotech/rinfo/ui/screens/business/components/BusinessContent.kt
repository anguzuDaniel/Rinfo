package com.danotech.rinfo.ui.screens.business.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.view.Window
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.model.Business
import com.danotech.rinfo.model.Review
import com.danotech.rinfo.ui.screens.business.BusinessTabs
import com.danotech.rinfo.ui.screens.business.BusinessViewModel
import com.danotech.rinfo.ui.screens.business.ImageItem
import com.danotech.rinfo.ui.screens.review.ReviewScreenViewModel

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun BusinessContent(
    loading: Boolean,
    businessId: String,
    viewModel: BusinessViewModel,
    reviewScreenViewModel: ReviewScreenViewModel,
    modifier: Modifier = Modifier,
    business: Business,
    onShowReviewPageClicked: () -> Unit = {},
    onDirectionClicked: (String) -> Unit = {},
    window: Window,
    onAddReviewButtonClick: () -> Unit = {},
    reviews: List<Review>,
    onShowBusinessPhotos: () -> Unit = {},
) {
    val context = LocalContext.current

    var tabState by remember { mutableStateOf(0) }
    val titles = listOf("About", "Gallery", "Reviews")

    val defaultProfilePicture: Bitmap = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.no_image
    )

    var addToFavorite by remember {
        mutableStateOf(false)
    }

    val imageList = remember {
        mutableStateListOf<ImageItem>()
    }

    // Create a launcher for selecting multiple images using the GetMultipleContents contract
    val launchMultipleImages = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri>? ->
        uris?.forEach { uri ->
            // Use the content resolver to load the image
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            val selectedBitmap = ImageDecoder.decodeBitmap(source)

            // Add the selected image to the list
            imageList.add(ImageItem(selectedBitmap))
        }
    }

    val downloadedImages = remember { mutableStateListOf<Bitmap>() }

    LaunchedEffect(key1 = Unit) {
        downloadImages(
            businessId = businessId,
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

    val scrollState = rememberScrollState()

    LazyColumn(
        modifier = modifier.windowInsetsPadding(
            WindowInsets.systemBars.only(WindowInsetsSides.Horizontal)
        )
    ) {
        item {
            val size = 300.dp
            Box(
                modifier = modifier
                    .height(size)
                    .fillMaxWidth()
                    .background(Color.White),
                contentAlignment = Alignment.BottomEnd
            ) {
                if (imageList.isEmpty() && downloadedImages.isEmpty()) {
                    Image(
                        bitmap = defaultProfilePicture.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                BusinessImageShimmer(
                    isLoading = imageList.isEmpty() && downloadedImages.isEmpty()
                ) {

                    /**
                     * if image is loading the the default image is added
                     *
                     */
                    /**
                     * if image is loading the the default image is added
                     *
                     */
                    /**
                     * if image is loading the the default image is added
                     *
                     */

                    /**
                     * if image is loading the the default image is added
                     *
                     */
                    if (downloadedImages.isEmpty()) {
                        ImageListViewImageItem(
                            imageList = imageList,
                            scrollState = scrollState,
                            size = size
                        )
                    } else {
                        ImageListViewBitmap(
                            imageList = downloadedImages,
                            scrollState = scrollState,
                            size = size,
                            window = window
                        )
                    }
                }
            }
        }

        item {
            Column(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.body_padding)),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = business.name,
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.ExtraBold
                    )

                    // if business is added to favorites then primary color is used for tint else white
                    if (!addToFavorite) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = stringResource(R.string.bookmark_business),
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.clickable {
                                addToFavorite = !addToFavorite
                            }
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = stringResource(R.string.bookmark_business),
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.clickable {
                                addToFavorite = !addToFavorite
                            }
                        )
                    }
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconAndText(
                        icon = Icons.Filled.Star,
                        iconDes = "ratings",
                        text = "${business.reviews}.0 / 5.0 (${business.reviews} reviews)"
                    )

                    IconAndText(
                        icon = Icons.Filled.LocationOn,
                        iconDes = stringResource(id = R.string.location),
                        text = business.address
                    )

                    IconAndText(
                        icon = Icons.Filled.Favorite,
                        iconDes = stringResource(id = R.string.favorites),
                        text = "${business.reviews} recommendations"
                    )
                }
            }
        }

        item {
            Column(
                modifier = Modifier
                    .padding(
                        vertical = 8.dp,
                        horizontal = dimensionResource(id = R.dimen.body_padding)
                    )
                    .fillMaxWidth(),
            ) {
                ActionDetailsRow(
                    businessName = business.name,
                    email = business.email,
                    phone = business.phone,
                    whatsapp = business.phone,
                    onDirectionClicked = {
                        onDirectionClicked(business.address)
                    },
                )
            }
        }


        item {
            /**
             * Subsection tags
             * when clicked they switch to the tab that matches the index
             */
            /**
             * Subsection tags
             * when clicked they switch to the tab that matches the index
             */
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                TabRow(selectedTabIndex = tabState) {
                    titles.forEachIndexed { index, title ->
                        Tab(
                            selected = tabState == index,
                            onClick = { tabState = index },
                            text = {
                                Text(
                                    text = title,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    color = if (tabState == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                                )
                            }
                        )
                    }
                }
            }
        }

        item {
            BusinessTabs(
                tabState = tabState,
                viewModel = viewModel,
                imageList = imageList,
                business = business,
                reviewScreenViewModel = reviewScreenViewModel,
                onAddReviewButtonClick = onAddReviewButtonClick,
                onShowReviewPageClicked = onShowReviewPageClicked,
                reviews = reviews,
                launchMultipleImages = launchMultipleImages,
                loading = loading,
                onShowBusinessPhotos = onShowBusinessPhotos
            )
        }
    }
}