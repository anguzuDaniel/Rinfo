package com.danotech.rinfo.ui.screens.business_account

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.ProfileImage
import com.danotech.rinfo.ui.components.RinfoButton
import com.danotech.rinfo.ui.components.TextInputWithLabel

/**
 * BusinessAccountContent
 * @param context: Context
 * @param uiState: BusinessAccountUiState
 * @param viewModel: BusinessAccountViewModel
 * @param modifier
 * @param innerPadding has a default of PaddingValues(0.dp)
 * @param onNextButtonClick
 */
@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun BusinessAccountContent(
    context: Context,
    uiState: BusinessAccountUiState,
    viewModel: BusinessAccountViewModel,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(0.dp),
    onNextButtonClick: () -> Unit = {}
) {
    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = modifier.padding(dimensionResource(id = R.dimen.body_padding)),
            contentPadding = innerPadding
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val imageSize = 150.dp

                    Surface(
                        onClick = {
                            showDialog = true
                            viewModel.openBottomSheet(true)
                        },
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = CircleShape
                    ) {
                        Box(
                            modifier = Modifier.size(dimensionResource(id = R.dimen.profile_image_size_large)),
                            contentAlignment = Alignment.Center
                        ) {
                            ProfileImage(
                                image = uiState.logo,
                                size = imageSize,
                                context = context
                            )
                        }

                        Box(
                            modifier = Modifier.size(dimensionResource(id = R.dimen.profile_image_size_large)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.CameraAlt,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                    }
                    Text("Add photo")
                }
            }

            item {
                AnimatedVisibility(visible = uiState.hasMessage) {
                    Text(
                        text = uiState.message,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(0.44f)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            item {
                TextInputWithLabel(
                    labelText = stringResource(R.string.business_name),
                    placeholder = R.string.cake_business,
                    value = uiState.name,
                    onValueChanged = viewModel::onNameChange
                )
            }

            item {
                TextInputWithLabel(
                    labelText = stringResource(R.string.description),
                    placeholder = R.string.placeholder_business_description,
                    value = uiState.description,
                    onValueChanged = viewModel::onDescriptionChange
                )
            }

            item {
                TextInputWithLabel(
                    labelText = stringResource(R.string.address),
                    value = uiState.address,
                    placeholder = R.string.placeholder_business_address,
                    onValueChanged = viewModel::onAddressChange
                )
            }

            item {
                TextInputWithLabel(
                    labelText = stringResource(id = R.string.phone),
                    value = uiState.phone,
                    placeholder = R.string.placeholder_business_phone,
                    onValueChanged = viewModel::onPhoneChange
                )
            }

            item {
                TextInputWithLabel(
                    labelText = stringResource(id = R.string.whatsapp),
                    value = uiState.whatsapp,
                    placeholder = R.string.whatsapp,
                    onValueChanged = viewModel::onWhatsappChange
                )
            }

            item {
                TextInputWithLabel(
                    labelText = stringResource(id = R.string.email),
                    value = uiState.email,
                    placeholder = R.string.placeholder_business_email,
                    onValueChanged = viewModel::onEmailChange
                )
            }

            item {
                RinfoButton(
                    name = R.string.next_page,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onNextButtonClick,
                    enabled = true
                )
            }
        }
    }
}