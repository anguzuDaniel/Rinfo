package com.danotech.rinfo.ui.screens.business_account

import android.graphics.Bitmap
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.BusinessAccountButton
import com.danotech.rinfo.ui.screens.dropdown.SelectBusinessCategory

/**
 * This page just shows the Business Account page
 * Let's the user select a category
 * This might change in the future to add more information
 */
@Composable
fun BusinessActionSectionPage(
    uiState: BusinessAccountUiState,
    bitmap: MutableState<Bitmap>,
    viewModel: BusinessAccountViewModel,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(0.dp),
    onSave: () -> Unit,
    onFailure: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(innerPadding)
            .padding(dimensionResource(id = R.dimen.body_padding)),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        AnimatedVisibility(visible = uiState.hasMessage) {
            Text(
                text = uiState.message,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(0.44f)
            )
        }

        SelectBusinessCategory(
            selected = uiState.businessCategory,
            onCategorySelected = viewModel::onCategoryChange
        )

        BusinessAccountButton(
            isLoading = uiState.isLoading, modifier = Modifier
                .fillMaxWidth()
        ) {
            viewModel.upLoadImageToFireBase(bitmap.value)
            viewModel.onBusinessAccountCreated(onSuccess = onSave) {
                onFailure(it)
            }
            onSave()
        }
    }
}
