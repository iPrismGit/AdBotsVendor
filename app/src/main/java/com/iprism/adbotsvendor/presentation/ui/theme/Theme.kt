package com.iprism.adbotsvendor.presentation.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = DarkBlue,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onPrimary = White,
    onSecondary = White,
    onTertiary = White,
    onBackground = White,
    onSurface = White,
    error = Red,
    onError = White,
    surfaceVariant = Color(0xFF2C2C2C),
    onSurfaceVariant = White,
    outlineVariant = Color(0xFF444444)
)

private val LightColorScheme = lightColorScheme(
    primary = DarkBlue,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = White,
    surface = White,
    onPrimary = White,
    onSecondary = White,
    onTertiary = White,
    onBackground = BLACK1,
    onSurface = BLACK1,
    error = Red,
    onError = White,
    surfaceVariant = LightBlue1,
    onSurfaceVariant = BLACK1,
    outlineVariant = LightGrey1
)

@Composable
fun AdBotsVendorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}