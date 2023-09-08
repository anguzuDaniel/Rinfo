package com.danotech.rinfo.data.preferences

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

//@Composable
//fun DarkModeSwitch(themePreferences: ThemePreferences) {
//    var isDark by remember { mutableStateOf(false) }
//
//    LaunchedEffect(key1 = isDark) {
//        themePreferences.isDarkTheme.collect { isDark = it }
//    }
//
//    Row(modifier = Modifier.padding(16.dp)) {
//        Switch(
//            checked = isDark,
//            onCheckedChange = { newIsDark ->
//                isDark = newIsDark
//                themePreferences.toggleTheme(newIsDark)
//            },
//            modifier = Modifier.padding(end = 8.dp)
//        )
//        Spacer(modifier = Modifier.width(8.dp))
//        Text(text = "Dark Mode")
//    }
//}
