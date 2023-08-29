package com.danotech.rinfo.ui.screens

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext

@Composable
fun SystemBroadCastReceiver(
    systemAction: String,
    onSystemAction: (intent: Intent?) -> Unit
) {

    val context = LocalContext.current
    val currentOnSystemEvent by rememberUpdatedState(onSystemAction)

    DisposableEffect(context, systemAction) {
        val intentFilter = IntentFilter(systemAction)
        val broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                currentOnSystemEvent(intent)
            }
        }
        context.registerReceiver(broadcastReceiver, intentFilter)
        onDispose {
            context.unregisterReceiver(broadcastReceiver)
        }
    }
}