package com.danotech.rinfo

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.danotech.rinfo.ui.RinfoApp
import com.danotech.rinfo.ui.screens.permission.RequestPermission
import com.danotech.rinfo.ui.theme.AppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.hilt.android.AndroidEntryPoint
import com.google.accompanist.permissions.*
import android.Manifest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                RequestPermission(permission = Manifest.permission.ACCESS_FINE_LOCATION)
                RinfoApp()
            }
        }
    }
}


