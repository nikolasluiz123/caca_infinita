package br.com.schmittsolucoes.cacainfinita.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    background = BackgroundDark,
    surfaceContainerHighest = SurfaceContainerHighestDark,
    surfaceContainer = SurfaceContainerDark,
    surfaceContainerLow = SurfaceContainerLowDark,
    primary = PrimaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = PrimaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondaryContainer = SecondaryContainerDark
)

private val LightColorScheme = lightColorScheme(
    background = BackgroundLight,
    surfaceContainerHighest = SurfaceContainerHighestLight,
    surfaceContainer = SurfaceContainerLight,
    surfaceContainerLow = SurfaceContainerLowLight,
    primary = PrimaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = PrimaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondaryContainer = SecondaryContainerLight
)

@Composable
fun CacaInfinitaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}