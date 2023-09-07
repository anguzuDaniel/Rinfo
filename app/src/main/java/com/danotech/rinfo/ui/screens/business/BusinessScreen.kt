@file:Suppress("KDocUnresolvedReference", "DEPRECATION")

package com.danotech.rinfo.ui.screens.business

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.Window
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Whatsapp
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.palette.graphics.Palette
import com.danotech.rinfo.R
import com.danotech.rinfo.common.SnackbarManager
import com.danotech.rinfo.model.Business
import com.danotech.rinfo.ui.components.BusinessImageButton
import com.danotech.rinfo.ui.components.RinfoButton
import com.danotech.rinfo.ui.components.RinfoFAB
import com.danotech.rinfo.ui.components.TruncateText
import com.danotech.rinfo.ui.screens.appbars.RinfoTopAppBar
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume

@RequiresApi(Build.VERSION_CODES.Q)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BusinessScreen(
    businessId: String,
    viewModel: BusinessViewModel = hiltViewModel(),
    onBackPressed: () -> Unit = {},
    onFabBtnClicked: () -> Unit = {},
    onShowReviewPageClicked: () -> Unit = {},
    window: Window,
    onDirectionClicked: (String) -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()

    BackHandler {
        onBackPressed()
    }

    LaunchedEffect(viewModel) {
        viewModel.getBusinessById(businessId)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = if (uiState.isLoading) {
            {}
        } else {
            {
                RinfoTopAppBar(
                    isShowingHomePage = false,
                    showBackgroundColor = false,
                    onBackButtonClicked = onBackPressed,
                    actions = {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // if the dropdown action is clicked
                            if (uiState.currentBusiness.userId == FirebaseAuth.getInstance().currentUser?.email) {
                                BusinessActionDropDown(
                                    onEditClicked = {},
                                    onDeleteClicked = {}
                                )
                            } else {
                                BusinessActionDropDown(
                                    onEditClicked = {},
                                    onDeleteClicked = {}
                                )
                            }
                        }
                    }
                )
            }
        },
        floatingActionButton = if (uiState.isLoading) {
            {}
        } else {
            { RinfoFAB(onClick = onFabBtnClicked) }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { innerPadding ->

        BusinessScreenShimmer(
            isLoading = uiState.isLoading
        ) {
            BusinessContent(
                businessId = businessId,
                business = uiState.currentBusiness,
                onShowReviewPageClicked = onShowReviewPageClicked,
                onDirectionClicked = {
                    onDirectionClicked(it)
                },
                viewModel = viewModel,
                modifier = Modifier.consumeWindowInsets(innerPadding),
                window = window
            )
        }
    }
}


@Composable
fun BusinessContent(
    businessId: String,
    viewModel: BusinessViewModel,
    modifier: Modifier = Modifier,
    business: Business,
    onShowReviewPageClicked: () -> Unit = {},
    onDirectionClicked: (String) -> Unit = {},
    window: Window
) {
    val context = LocalContext.current


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

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) {
        if (it != null) {
            imageList.add(ImageItem(it))
        }
    }

    val launchImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        if (Build.VERSION.SDK_INT < 28) {
            val selectedBitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
            imageList.add(ImageItem(selectedBitmap))
        } else {
            val source = it?.let { it1 ->
                ImageDecoder.createSource(context.contentResolver, it1)
            }
            val selectedBitmap = source?.let { it1 -> ImageDecoder.decodeBitmap(it1) }
            if (selectedBitmap != null) {
                imageList.add(ImageItem(selectedBitmap))
            }
        }
    }

    var isShowingAllDescriptionText by remember {
        mutableStateOf(false)
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

    val clickableText = if (isShowingAllDescriptionText) "less" else "Read more"
    val scrollState = rememberScrollState()
    val systemUiController = rememberSystemUiController()

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
                            systemUiController = systemUiController,
                            window = window
                        )
                    }
                }
            }
        }

        /**
         * If userId is equal to the current logged in in user then
         * show add image buttons
         */
        if (business.userId == FirebaseAuth.getInstance().currentUser?.email) {
            item {
                Row(
                    modifier = Modifier.padding(
                        vertical = 8.dp,
                        horizontal = dimensionResource(id = R.dimen.body_padding)
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                ) {
                    BusinessImageButton(
                        icon = R.drawable.baseline_camera_alt_24,
                        name = R.string.take_picture,
                        onClicked = {
                            launcher.launch(null)
                        },
                        modifier = Modifier.weight(1f)
                    )

                    BusinessImageButton(
                        icon = R.drawable.baseline_image_24,
                        name = R.string.from_gallery,
                        onClicked = {
                            launchImage.launch("image/*")
                        },
                        modifier = Modifier.weight(1f)
                    )
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
            var state by remember { mutableStateOf(0) }
            val titles = listOf("About", "Gallery", "Reviews")
            Column {
                TabRow(selectedTabIndex = state) {
                    titles.forEachIndexed { index, title ->
                        Tab(
                            selected = state == index,
                            onClick = { state = index },
                            text = {
                                Text(
                                    text = title,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    color = if (state == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                                )
                            }
                        )
                    }
                }
            }
        }


        item {
            Column(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.body_padding))
                    .fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(id = R.string.description),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(modifier = Modifier.height(8.dp))

                if (!isShowingAllDescriptionText) {
                    TruncateText(
                        text = business.description,
                        maxWords = 20,  // Set the desired maximum number of words
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                } else {
                    Text(
                        text = business.description,
                        style = TextStyle(
                            fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                        ),
                        textAlign = TextAlign.Justify,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                ClickableText(
                    text = AnnotatedString(clickableText),
                    style = MaterialTheme.typography.bodyMedium
                        .copy(color = MaterialTheme.colorScheme.primary),
                    onClick = { isShowingAllDescriptionText = !isShowingAllDescriptionText }
                )
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
                OutlinedButton(
                    onClick = onShowReviewPageClicked,
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(
                        1.dp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.see_reviews),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

                if (business.userId == FirebaseAuth.getInstance().currentUser?.email) {
                    RinfoButton(
                        name = R.string.save_change,
                        onClicked = {
                            if (imageList.isNotEmpty()) {
                                val imageBitmapList = imageList.map { imageItem ->
                                    imageItem.bitmap
                                }

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
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun ActionDetailsRow(
    businessName: String,
    email: String = "",
    whatsapp: String = "",
    phone: String = "",
    onDirectionClicked: () -> Unit = {}
) {
    val context = LocalContext.current
    val sendEmailLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        // Handle the result if needed
    }

    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")  // This ensures only email apps are selected
        putExtra(Intent.EXTRA_EMAIL, arrayOf(email)) // Email address
        putExtra(Intent.EXTRA_SUBJECT, "Subject") // Email subject
        putExtra(Intent.EXTRA_TEXT, "Hello,") // Email body
    }

    val message = "Hello ${businessName}!" // Message content

    val whatsappIntent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse("https://api.whatsapp.com/send?phone=$whatsapp&text=$message")
    }

    val dialerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        // Handle the result if needed
    }

    val callIntent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phone")
    }

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        item {
            CallToActionButton(
                icon = Icons.Filled.Phone,
                name = R.string.call,
                onClicked = {
                    dialerLauncher.launch(callIntent)
                },
            )
        }

        item {
            CallToActionButton(
                icon = Icons.Filled.Whatsapp,
                name = R.string.whatsapp,
                onClicked = {
                    context.startActivity(whatsappIntent)
                },
            )
        }

        item {
            CallToActionButton(
                icon = Icons.Filled.Email,
                name = R.string.email,
                onClicked = {
                    sendEmailLauncher.launch(emailIntent)
                },
            )
        }

        item {
            CallToActionButton(
                icon = Icons.Filled.Directions,
                name = R.string.directions,
                onClicked = onDirectionClicked,
            )
        }

        item {
            CallToActionButton(
                icon = Icons.Filled.Share,
                name = R.string.share,
                onClicked = { /*TODO*/ },
            )
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
                    continuation.cancel()
                }
            }
    }


@Preview
@Composable
fun ActionPreview() {
    ActionDetailsRow(
        businessName = ""
    )
}