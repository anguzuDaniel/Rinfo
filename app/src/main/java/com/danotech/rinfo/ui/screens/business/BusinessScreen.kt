package com.danotech.rinfo.ui.screens.business

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.common.SnackbarManager
import com.danotech.rinfo.model.Business
import com.danotech.rinfo.ui.components.BusinessImageButton
import com.danotech.rinfo.ui.components.RatingStars
import com.danotech.rinfo.ui.components.RinfoButton
import com.danotech.rinfo.ui.components.RinfoFAB
import com.danotech.rinfo.ui.components.TruncateText
import com.danotech.rinfo.ui.screens.appbars.RinfoTopAppBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow

@RequiresApi(Build.VERSION_CODES.Q)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BusinessScreen(
    businessId: String,
    reviewerUserId: String,
    viewModel: BusinessViewModel = hiltViewModel(),
    onBackPressed: () -> Unit = {},
    onSearchIconClicked: () -> Unit = {},
    onFabBtnClicked: () -> Unit = {},
    onShowReviewPageClicked: () -> Unit = {},
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
    ) {
        if (!uiState.isLoading) {
            BusinessContent(
                business = uiState.currentBusiness,
                onShowReviewPageClicked = onShowReviewPageClicked,
                onDirectionClicked = {
                    onDirectionClicked(it)
                },
                viewModel = viewModel
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .animateContentSize(
                        animationSpec = (tween(
                            durationMillis = 300,
                            easing = LinearOutSlowInEasing
                        ))
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Composable
fun collectBusinessState(businessFlow: Flow<Business?>): State<Business?> {
    // Collect the flow and convert it into a Compose State
    return businessFlow.collectAsState(initial = null)
}

@Composable
fun BusinessContent(
    viewModel: BusinessViewModel,
    modifier: Modifier = Modifier,
    business: Business,
    onShowReviewPageClicked: () -> Unit = {},
    onDirectionClicked: (String) -> Unit = {},
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

    val imagesToRetrieve = 3 // Replace with the number of images you want to retrieve
    val retrievedImages = mutableListOf<Bitmap>()

    for (index in 0 until imagesToRetrieve) {
        getBusinessImages(
            businessId = business.userId,
            index = index,
            onSuccess = { bitmap ->
                retrievedImages.add(bitmap)
                if (retrievedImages.size == imagesToRetrieve) {
//                        uiState.currentBusinessImages = retrievedImages
                    viewModel.addImages(retrievedImages)
                }
            },
            onError = { exception ->
                // Handle error
            }
        )
    }

    val uiState = viewModel.uiState.collectAsState().value

    val clickableText = if (isShowingAllDescriptionText) "less" else "Read more"

    Column {
        Box(
            modifier = modifier
                .height(250.dp)
                .fillMaxWidth()
                .background(Color.White),
            contentAlignment = Alignment.BottomEnd
        ) {
            /**
             * if image is loading the the default image is added
             *
             */
            if (uiState.currentBusinessImages.isEmpty()) {
                ImageListViewImageItem(
                    imageList = imageList,
                )
            } else {
                ImageListViewBitmap(
                    imageList = uiState.currentBusinessImages
                )
            }
        }


        LazyColumn(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.body_padding))
        ) {
            /**
             * If userId is equal to the current logged in in user then
             * show add image buttons
             */
            if (business.userId == FirebaseAuth.getInstance().currentUser?.email) {
                item {
                    Row(
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
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
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
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Image(
                            modifier = Modifier
                                .size(20.dp),
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant)
                        )

                        Text(
                            text = "${business.reviews}.0 / 5.0",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold
                        )

                        Text(
                            text = "( ${business.reviews} reviews )",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }

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
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_medium)))
            }

            item {
                ActionDetailsRow(
                    businessName = business.name,
                    email = business.email,
                    phone = business.phone,
                    whatsapp = business.phone,
                    onDirectionClicked = {
                        onDirectionClicked(business.address)
                    },
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_medium)))
            }

            item {
                Text(
                    text = stringResource(id = R.string.description),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
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

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_medium)))
            }

            item {
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
            }

            if (business.userId == FirebaseAuth.getInstance().currentUser?.email) {
                item {
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
                                    onError = { exception ->
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
    ) { _ ->
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
    ) { _ ->
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
//                modifier = Modifier.weight(1f)
            )
        }


        item {
            CallToActionButton(
                icon = Icons.Filled.Whatsapp,
                name = R.string.whatsapp,
                onClicked = {
                    context.startActivity(whatsappIntent)
                },
//                modifier = Modifier.weight(1f)
            )
        }

        item {
            CallToActionButton(
                icon = Icons.Filled.Email,
                name = R.string.email,
                onClicked = {
                    sendEmailLauncher.launch(emailIntent)
                },
//                modifier = Modifier.weight(1f)
            )
        }


        item {
            CallToActionButton(
                icon = Icons.Filled.Directions,
                name = R.string.directions,
                onClicked = onDirectionClicked,
//                modifier = Modifier.weight(1f)
            )
        }

        item {
            CallToActionButton(
                icon = Icons.Filled.Share,
                name = R.string.share,
                onClicked = { /*TODO*/ },
//                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun CallToActionButton(
    icon: ImageVector,
    name: Int,
    onClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(shape = MaterialTheme.shapes.medium)
        ) {
            IconButton(
                onClick = onClicked,
                modifier = modifier
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Icon(
                    imageVector = icon,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    contentDescription = stringResource(id = name)
                )
            }
        }

        Text(
            text = stringResource(id = name),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

//@Composable
//fun CallToActionButton(
//    icon: ImageVector,
//    name: Int,
//    onClicked: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Box(
//        modifier = Modifier
//            .clip(CircleShape)
//            .background(MaterialTheme.colorScheme.surfaceVariant)
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.spacedBy(2.dp)
//        ) {
//            IconButton(
//                onClick = onClicked,
//                modifier = Modifier
//                    .background(MaterialTheme.colorScheme.surfaceVariant),
//                colors = IconButtonDefaults.iconButtonColors(
//                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
//                    containerColor = MaterialTheme.colorScheme.surfaceVariant
//                )
//            ) {
//                Icon(
//                    imageVector = icon,
//                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
//                    contentDescription = stringResource(id = name)
//                )
//            }
//
//            Text(
//                text = stringResource(id = name),
//                style = MaterialTheme.typography.labelSmall,
//                color = MaterialTheme.colorScheme.onBackground
//            )
//        }
//    }
//}


@Composable
fun RecommendButton(
    onClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    RinfoButton(
        name = R.string.recommend,
        onClicked = onClicked,
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.medium)
    )
}


@Composable
fun IconAndText(
    icon: ImageVector,
    iconDes: String,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = iconDes,
            modifier = Modifier.size(15.dp),
        )

        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Light
        )
    }
}

@Composable
fun RatingRow(
    reviews: Int = 0
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RatingStars(rating = reviews)

        Text(
            text = "${reviews}.0",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.ExtraBold
        )
    }
    Spacer(modifier = Modifier.padding(5.dp))
}

/**
 * Gets images from FireStorage
 * @param businessId current business id
 * @param index the index of the images
 * @param onSuccess function called if successful
 * @param onError function called if there is an error
 */
private fun getBusinessImages(
    businessId: String,
    index: Int,
    onSuccess: (Bitmap) -> Unit,
    onError: (Exception) -> Unit
) {

    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference

    val imageName = "${businessId}_${index}.jpg"
    val imageRef = storageRef.child("business_images/${imageName}")

    imageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener { bytes ->
        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        onSuccess(bitmap)
    }.addOnFailureListener { exception ->
        onError(exception)
    }
}

@Preview
@Composable
fun ActionPreview() {
    ActionDetailsRow(
        businessName = ""
    )
}