package br.com.schmittsolucoes.cacainfinita.presentation.camera.composables.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import br.com.schmittsolucoes.cacainfinita.R
import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.TorchMode

@Composable
fun FlashToggle(
    torchMode: TorchMode,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onToggle,
        modifier = modifier
            .clip(CircleShape)
            .background(Color.Black.copy(alpha = 0.4f))
    ) {
        val (iconRes, textRes) = when (torchMode) {
            TorchMode.AUTO -> R.drawable.ic_flash_auto_24dp to R.string.flash_auto
            TorchMode.ON -> R.drawable.ic_flash_on_24dp to R.string.flash_on
            TorchMode.OFF -> R.drawable.ic_flash_off_24dp to R.string.flash_off
        }
        
        Box(contentAlignment = Alignment.Center) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = stringResource(id = textRes),
                tint = if (torchMode == TorchMode.OFF) Color.Gray else Color.White
            )
        }
    }
}