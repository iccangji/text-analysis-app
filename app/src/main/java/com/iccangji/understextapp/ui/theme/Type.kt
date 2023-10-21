package com.iccangji.understextapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.iccangji.understextapp.R

val InterTight = FontFamily(
    Font(R.font.inter_tight_regular)
)

val InterTightBold = FontFamily(
    Font(R.font.inter_tight_bold)
)

val Gabarito = FontFamily(
    Font(R.font.gabarito_bold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = InterTight,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Gabarito,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    displayLarge = TextStyle(
        fontFamily = Gabarito,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    displayMedium = TextStyle(
        fontFamily = InterTight,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = InterTight,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.sp
    ),
    labelMedium = TextStyle(
        fontFamily = InterTightBold,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        letterSpacing = 0.sp
    ),
    /* Other default text styles to override
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)