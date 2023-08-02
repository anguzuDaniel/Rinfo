package com.danotech.rinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.danotech.rinfo.ui.RinfoApp
import com.danotech.rinfo.ui.screens.Home.HomeScreen
import com.example.compose.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                RinfoApp()
            }
        }
    }
}


