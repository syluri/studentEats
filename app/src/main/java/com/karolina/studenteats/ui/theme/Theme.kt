package com.karolina.studenteats.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val StudentEatsColorScheme = lightColorScheme(
    primary = PrimaryGreen,
    onPrimary = OnPrimaryWhite,
    primaryContainer = SurfaceHighlightGreen,
    onPrimaryContainer = TextPrimary,
    secondary = PrimaryGreen,
    onSecondary = OnPrimaryWhite,
    secondaryContainer = SurfaceHighlightGreen,
    onSecondaryContainer = TextPrimary,
    tertiary = PrimaryGreen,
    onTertiary = OnPrimaryWhite,
    background = BackgroundWhite,
    onBackground = TextPrimary,
    surface = BackgroundWhite,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceHighlightGreen,
    onSurfaceVariant = TextSecondary,
    error = ErrorRed,
    onError = OnPrimaryWhite,
    outline = OutlineSubtle
)

@Composable
fun StudentEatsTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = StudentEatsColorScheme,
        typography = Typography,
        content = content
    )
}
