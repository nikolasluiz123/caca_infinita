package br.com.schmittsolucoes.cacasobmedida.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val PrimaryLight = Color(0xFF24389C)
val onPrimaryLight = Color(0xFFFFFFFF)
val SecondaryContainerLight = Color(0xFFC9E7F7)
val BackgroundLight = Color(0xFFF4FAFF)
val SurfaceContainerHighestLight = Color(0xFFE6F6FF)
val SurfaceContainerLight = Color(0xFFD9F2FF)
val SurfaceContainerLowLight = Color(0xFFE6F6FF)

val CardAccentLight = Color(0xFF85F6E5)
val SecondaryTextColorLight = Color(0xFF454652)

val PrimaryDark = Color(0xFFDEE0FF)
val onPrimaryDark = Color(0xFF00105C)
val SecondaryContainerDark = Color(0x4DC5C5D4)
val BackgroundDark = Color(0xFF001F2A)
val SurfaceContainerHighestDark = Color(0xFF163440)
val SurfaceContainerDark = Color(0xFF163440)
val SurfaceContainerLowDark = Color(0xFF163440)

val CardAccentDark = Color(0xFF006A60)
val SecondaryTextColorDark = Color(0xFFC5C5D4)

val Gold = Color(0xFFFFD700)
val Silver = Color(0xFFC0C0C0)
val Bronze = Color(0xFFCD7F32)

@get:Composable
val CardAccentColor: Color
    get() = if (isSystemInDarkTheme()) CardAccentDark else CardAccentLight

@get:Composable
val SecondaryTextColor: Color
    get() = if (isSystemInDarkTheme()) SecondaryTextColorDark else SecondaryTextColorLight

