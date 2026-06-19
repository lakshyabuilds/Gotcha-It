package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme =
  darkColorScheme(
    primary = ArcadeAccent,
    secondaryContainer = ArcadeAccent.copy(alpha = 0.3f),
    onSecondaryContainer = ArcadeAccent,
    background = ArcadeBackground,
    surface = ArcadeSurface,
    onPrimary = ArcadeText,
    onBackground = ArcadeText,
    onSurface = ArcadeText
  )

@Composable
fun MyApplicationTheme(
  content: @Composable () -> Unit,
) {
  MaterialTheme(colorScheme = DarkColorScheme, typography = Typography, content = content)
}
