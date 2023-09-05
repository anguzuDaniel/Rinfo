package com.danotech.rinfo.ui.screens.splash

import android.os.Build
import android.view.Window
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.ui.components.BasicButton
import com.danotech.rinfo.R.string as AppText
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.screens.RInfoScreen
import kotlinx.coroutines.delay

private const val SPLASH_TIMEOUT = 1000L

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = hiltViewModel(),
    navigateTo: () -> Unit,
    window: Window
) {
    var startAnimation by remember {
        mutableStateOf(false)
    }
    val alphaAnimation = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000
        )
    )

    val view = LocalView.current
    val windowInsetsController =
        WindowCompat.getInsetsController(window, view)

    val useDarkIcons = !isSystemInDarkTheme()

    LaunchedEffect(Unit) {
        windowInsetsController.isAppearanceLightStatusBars = useDarkIcons
        window.statusBarColor = Color.Transparent.toArgb()
    }

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(4000)
        navigateTo()
    }

    Box(
        modifier = Modifier
            .background(if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = if (isSystemInDarkTheme()) painterResource(id = R.drawable.logo) else painterResource(
                id = R.drawable.logo_transparent
            ),
            contentDescription = stringResource(id = R.string.SplashScreen),
            modifier = Modifier.alpha(alphaAnimation.value)
        )
    }
}
