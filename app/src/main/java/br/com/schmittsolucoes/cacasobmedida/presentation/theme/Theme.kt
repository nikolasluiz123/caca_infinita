package br.com.schmittsolucoes.cacasobmedida.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    background = BackgroundDark,
    surfaceContainerHighest = SurfaceContainerHighestDark,
    surfaceContainer = SurfaceContainerDark,
    primary = PrimaryDark,
    onPrimary = onPrimaryDark,
    secondaryContainer = SecondaryContainerDark
)

private val LightColorScheme = lightColorScheme(
    background = BackgroundLight,
    surfaceContainerHighest = SurfaceContainerHighestLight,
    surfaceContainer = SurfaceContainerLight,
    primary = PrimaryLight,
    onPrimary = onPrimaryLight,
    secondaryContainer = SecondaryContainerLight
)

@Composable
fun CacaSobMedidaTheme(
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