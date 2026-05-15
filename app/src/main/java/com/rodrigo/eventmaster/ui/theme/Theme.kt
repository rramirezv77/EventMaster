package com.rodrigo.eventmaster.ui.theme

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
    primary = Teal80,
    secondary = Amber80,
    tertiary = Coral80,
    background = Ink,
    surface = Color(0xFF1F2937),
    onPrimary = Color(0xFF062F2B),
    onSecondary = Color(0xFF3B2500),
    onTertiary = Color(0xFF3B0909),
    onBackground = Cloud,
    onSurface = Cloud,
    onSurfaceVariant = Color(0xFFD1D5DB)
)

private val LightColorScheme = lightColorScheme(
    primary = Teal40,
    secondary = Amber40,
    tertiary = Coral40,
    background = Cloud,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Ink,
    onSurface = Ink,
    onSurfaceVariant = Color(0xFF4B5563)
)

@Composable
fun EventMasterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
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
