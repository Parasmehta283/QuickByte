package com.bluespacestudioz.quickbite.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val QuickBiteLightColorScheme = lightColorScheme(
    primary = QuickBitePrimary,
    onPrimary = QuickBiteOnPrimary,
    primaryContainer = QuickBitePrimaryContainer,
    onPrimaryContainer = QuickBiteOnPrimaryContainer,

    secondary = QuickBiteSecondary,
    onSecondary = QuickBiteOnSecondary,
    secondaryContainer = QuickBiteSecondaryContainer,
    onSecondaryContainer = QuickBiteOnSecondaryContainer,

    tertiary = QuickBiteTertiary,
    onTertiary = QuickBiteOnTertiary,
    tertiaryContainer = QuickBiteTertiaryContainer,
    onTertiaryContainer = QuickBiteOnTertiaryContainer,

    background = QuickBiteBackground,
    onBackground = QuickBiteOnBackground,

    surface = QuickBiteSurface,
    onSurface = QuickBiteOnSurface,
    surfaceVariant = QuickBiteSurfaceVariant,
    onSurfaceVariant = QuickBiteOnSurfaceVariant,

    outline = QuickBiteOutline,
    outlineVariant = QuickBiteOutlineVariant,

    error = QuickBiteError,
    onError = QuickBiteOnError,
    errorContainer = QuickBiteErrorContainer,
    onErrorContainer = QuickBiteOnErrorContainer
)

private val QuickBiteShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(24.dp)
)

@Composable
fun QuickBiteTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = QuickBiteLightColorScheme,
        typography = QuickBiteTypography,
        shapes = QuickBiteShapes,
        content = content
    )
}