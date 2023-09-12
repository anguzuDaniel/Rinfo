package com.danotech.rinfo.ui

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.danotech.rinfo.ui.screens.RInfoScreen

/**
 * Used to create a bottom menu item
 * @param selected is the item selected
 * @param selectedIcon is the icon of the item
 * @param label is the label of the item
 *
 */
data class BottomMenuItem(
    val rinfoScreen: RInfoScreen,
    val selected: Boolean = false,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    @StringRes val label: Int
)