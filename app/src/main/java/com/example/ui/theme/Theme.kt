package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.graphics.Color

private val AppColorScheme =
  darkColorScheme(
      primary = Emerald400,
      secondary = Emerald500,
      background = DarkBackground,
      surface = SurfaceLight,
      surfaceVariant = SurfaceDark,
      onPrimary = Color(0xFF064E3B),
      onBackground = TextPrimary,
      onSurface = TextPrimary,
      onSurfaceVariant = TextSecondary,
      outlineVariant = BorderColor
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = true, // Force dark theme
  // Dynamic color is available on Android 12+
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme = AppColorScheme

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
