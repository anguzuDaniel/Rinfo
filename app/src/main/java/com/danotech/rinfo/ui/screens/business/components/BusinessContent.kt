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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Reviews
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Tab
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.model.Business
import com.danotech.rinfo.model.Review
import com.danotech.rinfo.ui.screens.business.BusinessViewModel
import com.danotech.rinfo.ui.screens.business.ImageItem
import com.danotech.rinfo.ui.screens.business.subsections.BusinessAboutSection
import com.danotech.rinfo.ui.screens.business.subsections.BusinessReviewSection
import com.danotech.rinfo.ui.screens.home.staggeredItems
import com.danotech.rinfo.ui.screens.review.ReviewScreenViewModel

data class Tab(
    val icon: ImageVector,
    val title: String
)

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
    onProductClick: (String) -> Unit = {}
) {
    val context = LocalContext.current

    var tabState by remember { mutableStateOf(0) }
    val tabs = listOf(
        Tab(
            icon = Icons.Default.Tab,
            title = "About"
        ),
        Tab(
            icon = Icons.Filled.ShoppingCart,
            title = "Products"
        ),
        Tab(
            icon = Icons.Filled.Reviews,
            title = "Reviews"
        )
    )

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

    val products = getProductList()

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
            /**
             * Subsection tags
             * when clicked they switch to the tab that matches the index
             */
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TabRow(selectedTabIndex = tabState) {
                    tabs.forEachIndexed { index, tab ->
                        Tab(
                            selected = tabState == index,
                            onClick = { tabState = index },
                            text = {
                                Text(
                                    text = tab.title,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            },
                            icon = {
                                Icon(
                                    imageVector = tab.icon,
                                    contentDescription = tab.title,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        )
                    }
                }
            }
        }

        when (tabState) {
            0 -> item {
                BusinessAboutSection(business = business)
            }

//            1 -> BusinessGallerySection(
//                viewModel = viewModel,
//                business = business,
//                imageList = imageList,
//                launchMultipleImages = launchMultipleImages,
//                loading = loading,
//                onShowBusinessPhotos = onShowBusinessPhotos
//            )

            1 -> staggeredItems(
                data = products,
                columnCount = 2,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) { product ->
                ProductCard(
                    product = product,
                    onProductCardClick = {
                        onProductClick(product.productId)
                    },
                    onAddToCartClick = {

                    }
                )
            }


            2 -> item {
                BusinessReviewSection(
                    business = business,
                    reviewScreenViewModel = reviewScreenViewModel,
                    onAllButtonReviewClick = onShowReviewPageClicked,
                    onAddReviewButtonClick = onAddReviewButtonClick,
                    reviews = reviews
                )
            }

            else -> item {
                BusinessAboutSection(business = business)
            }
        }
    }
}

fun getProductList(): List<Product> {
    return listOf(
        Product(
            productId = "1",
            BusinessId = "Business1",
            image = "https://images.unsplash.com/photo-1602143407151-7111542de6e8?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2487&q=80",
            name = "stainless steel water bottle",
            description = "Description for Product 1",
            discount = "10%",
            price = "$20.99",
            rating = 4,
            reviews = 25,
            category = "Category A"
        ),
        Product(
            productId = "2",
            BusinessId = "Business2",
            image = "https://images.unsplash.com/photo-1546868871-7041f2a55e12?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2264&q=80",
            name = "Product 2",
            description = "Description for Product 2",
            discount = "15%",
            price = "$15.49",
            rating = 4,
            reviews = 30,
            category = "Category B"
        ),
        Product(
            productId = "3",
            BusinessId = "Business3",
            image = "https://images.unsplash.com/photo-1543512214-318c7553f230?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2487&q=80",
            name = "BlueTooth Speaker",
            description = "Description for Product 3",
            discount = "20%",
            price = "$25.99",
            rating = 5,
            reviews = 40,
            category = "Category A"
        ),
        Product(
            productId = "4",
            BusinessId = "Business4",
            image = "https://images.unsplash.com/photo-1564466809058-bf4114d55352?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2486&q=80",
            name = "Nikon Camera",
            description = "Description for Product 4",
            discount = "12%",
            price = "$18.75",
            rating = 4,
            reviews = 22,
            category = "Category C"
        ),
        Product(
            productId = "5",
            BusinessId = "Business5",
            image = "https://images.unsplash.com/photo-1524805444758-089113d48a6d?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2488&q=80",
            name = "Leather Watch",
            description = "Description for Product 5",
            discount = "8%",
            price = "$30.00",
            rating = 4,
            reviews = 27,
            category = "Category B"
        ),
        Product(
            productId = "6",
            BusinessId = "Business6",
            image = "https://images.unsplash.com/photo-1571380401583-72ca84994796?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2264&q=80",
            name = "Iphone 11",
            description = "Description for Product 6",
            discount = "10%",
            price = "$22.99",
            rating = 3,
            reviews = 18,
            category = "Category A"
        ),
        Product(
            productId = "7",
            BusinessId = "Business7",
            image = "https://images.unsplash.com/photo-1612548403247-aa2873e9422d?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2487&q=80",
            name = "Video Camera",
            description = "Description for Product 7",
            discount = "5%",
            price = "$40.49",
            rating = 5,
            reviews = 35,
            category = "Category C"
        ),
        Product(
            productId = "8",
            BusinessId = "Business8",
            image = "https://images.unsplash.com/photo-1589365278144-c9e705f843ba?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2487&q=80",
            name = "Wooden Bottle",
            description = "Description for Product 8",
            discount = "18%",
            price = "$14.99",
            rating = 4,
            reviews = 29,
            category = "Category A"
        ),
        Product(
            productId = "9",
            BusinessId = "Business9",
            image = "https://images.unsplash.com/photo-1584735174965-48c48d7edfde?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2487&q=80",
            name = "Cool sneaker",
            description = "Description for Product 9",
            discount = "25%",
            price = "$19.99",
            rating = 4,
            reviews = 31,
            category = "Category B"
        ),
        Product(
            productId = "10",
            BusinessId = "Business10",
            image = "https://images.unsplash.com/photo-1603487742131-4160ec999306?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2487&q=80",
            name = "Birkenstock Sandals",
            description = "Description for Product 10",
            discount = "15%",
            price = "$24.49",
            rating = 5,
            reviews = 45,
            category = "Category C"
        )
    )
}