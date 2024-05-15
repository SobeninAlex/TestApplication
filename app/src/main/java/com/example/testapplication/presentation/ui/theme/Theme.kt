package com.example.testapplication.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Black900,
    secondary = Black700,
    surface = Black700,
    onPrimary = Color.White,
    onPrimaryContainer = Color.White,
    onSecondary = Black500,
    onSurface = Black500,
    background = Black700,
)

private val LightColorScheme = lightColorScheme(
    primary = White200,
    secondary = Color.White,
    surface = Color.White,
    onPrimary = Black700,
    onPrimaryContainer = Black700,
    onSecondary = Black500,
    onSurface = Black500,
    background = Color.White,
)

@Composable
fun TestApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}