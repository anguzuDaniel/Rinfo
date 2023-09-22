package com.danotech.rinfo.ui.screens.profile

import android.graphics.Bitmap
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.ProfileButton
import com.danotech.rinfo.ui.components.ProfileImage
import com.danotech.rinfo.ui.components.TextInputWithLabel

@Composable
fun ProfileContent(
    bitmap: MutableState<Bitmap>,
    viewModel: ProfileViewModel,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsState().value

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.body_padding)),
        contentPadding = innerPadding,
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
                            image = uiState.profileImage,
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
            Spacer(modifier = Modifier.height(40.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(40.dp))
        }


        item {
            TextInputWithLabel(
                value = uiState.profileName,
                onValueChanged = viewModel::onProfileNameChanged,
                labelText = stringResource(R.string.profile_name)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            TextInputWithLabel(
                value = uiState.profileFirstName,
                onValueChanged = viewModel::onProfileFirstNameChanged,
                labelText = stringResource(R.string.first_name)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            TextInputWithLabel(
                value = uiState.profileLastName,
                onValueChanged = viewModel::profileLastNameChanged,
                labelText = stringResource(R.string.last_name)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            ProfileButton(
                modifier = Modifier
                    .fillMaxWidth(),
                isLoading = uiState.isLoading,
            ) {
                viewModel.saveProfile()
            }
        }
    }
}