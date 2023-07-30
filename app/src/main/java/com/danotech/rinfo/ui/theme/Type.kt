package com.danotech.rinfo.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.danotech.rinfo.R

val OPEN_SANS = FontFamily(
    Font(R.font.open_sans_bold),
    Font(R.font.open_sans_light),
    Font(R.font.open_sans_regular),
)

val ROBOTO = FontFamily(
    Font(R.font.roboto_bold),
    Font(R.font.roboto_medium),
)

val Montserrat = FontFamily(
    Font(R.font.montserrat_bold),
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = OPEN_SANS,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = OPEN_SANS,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.5.sp
    ),
    displayMedium = TextStyle(
        fontFamily = ROBOTO,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp
    ),
    labelSmall = TextStyle(
        fontFamily = OPEN_SANS,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
)