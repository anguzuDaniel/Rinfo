@file:JvmName("DirectionKt")

package com.danotech.rinfo.ui.screens.map

import android.annotation.SuppressLint
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.Loading

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    city: String,
    country: String,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onClose: () -> Unit = {},
    viewModel: MapViewModel = hiltViewModel(),
) {
    BackHandler {
        onBack()
    }

    var active by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(viewModel) {
        viewModel.searchBusinesses()
    }

    val uiState = viewModel.uiState.collectAsState().value

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Transparent),
                title = {},
                navigationIcon = {
                    Icon(
                        modifier = Modifier.clickable { onBack() },
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_button)
                    )
                }
            )
        }
    ) { _ ->
        if (uiState.isLoading) {
            Loading()
        } else {
            MapDisplay(
                viewModel = viewModel,
                country = country,
                city = city
            )
        }
    }
}