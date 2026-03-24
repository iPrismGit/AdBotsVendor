package com.iprism.adbotsvendor.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.iprism.adbotsvendor.R


val MontserratFamily = FontFamily(
    Font(R.font.montserrat_regular, FontWeight.Normal),
    Font(R.font.montserrat_bold, FontWeight.Bold),
    Font(R.font.montserrat_light, FontWeight.Light),
    Font(R.font.montserrat_thin, FontWeight.Thin),
    Font(R.font.montserrat_semibold, FontWeight.SemiBold),
    Font(R.font.montserrat_black, FontWeight.Black),
    Font(R.font.montserrat_medium, FontWeight.Medium),
    Font(R.font.montserrat_extrabold, FontWeight.ExtraBold),
    Font(R.font.montserrat_extrabold, FontWeight.ExtraLight),
)

// Set of Material typography styles to start with
val Typography = Typography(

    // For big Texts

    displayLarge = TextStyle(
        fontFamily = MontserratFamily,
        fontSize = 96.sp,
        fontWeight = FontWeight.Light,
        lineHeight = 117.sp,
        letterSpacing = (-1.5).sp
    ),
    displayMedium = TextStyle(
        fontFamily = MontserratFamily,
        fontSize = 60.sp,
        fontWeight = FontWeight.Light,
    ),
    displaySmall = TextStyle(
        fontFamily = MontserratFamily,
        fontSize = 48.sp,
        fontWeight = FontWeight.Normal,
    ),

    // For Headlines

    headlineLarge =  TextStyle(
        fontFamily = MontserratFamily,
        fontSize = 30.sp,
        fontWeight = FontWeight.SemiBold,
    ),

    headlineMedium = TextStyle(
        fontFamily = MontserratFamily,
        fontSize = 30.sp,
        fontWeight = FontWeight.SemiBold,
    ),
    headlineSmall = TextStyle(
        fontFamily = MontserratFamily,
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
    ),

    // For Text Titles
    titleLarge = TextStyle(
        fontFamily = MontserratFamily,
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
    ),
    titleMedium = TextStyle(
        fontFamily = MontserratFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
    ),
    titleSmall = TextStyle(
        fontFamily = MontserratFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
    ),

    // For TextFields

    bodyLarge = TextStyle(
        fontFamily = MontserratFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
    ),
    bodyMedium = TextStyle(
        fontFamily = MontserratFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
    ),
    bodySmall = TextStyle(
        fontFamily = MontserratFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal
    ),

    // For Buttons
    labelMedium = TextStyle(
        fontFamily = MontserratFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
    ),
    labelLarge = TextStyle(
        fontFamily = MontserratFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
    ),
    labelSmall = TextStyle(
        fontFamily = MontserratFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold
    )
)