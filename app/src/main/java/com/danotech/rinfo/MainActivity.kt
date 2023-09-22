package com.danotech.rinfo

//noinspection UsingMaterialAndMaterial3Libraries
import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.danotech.rinfo.ui.RinfoApp
import com.danotech.rinfo.ui.screens.permission.RequestPermission
import com.danotech.rinfo.ui.theme.AppTheme
import com.google.accompanist.permissions.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.Q)
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This app draws behind the system bars, so we want to handle fitting system windows
        WindowCompat.setDecorFitsSystemWindows(window, false)

        installSplashScreen()

        setContent {
            AppTheme {
                RequestPermission(permission = Manifest.permission.ACCESS_FINE_LOCATION)
                RinfoApp(
                    window
                )
            }
        }
    }
}


