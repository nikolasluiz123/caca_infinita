package br.com.schmittsolucoes.cacainfinita.presentation.camera.composables.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CaptureButton(
    onClick: () -> Unit,
    isEnabled: Boolean,
    isProcessing: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(80.dp)
            .clip(CircleShape)
            .background(if (isEnabled && !isProcessing) Color.White else Color.White.copy(alpha = 0.4f))
            .border(4.dp, Color.Gray.copy(alpha = 0.5f), CircleShape)
            .padding(4.dp)
    ) {
        IconButton(
            onClick = onClick,
            enabled = isEnabled && !isProcessing,
            modifier = Modifier.fillMaxSize()
        ) {
            if (isProcessing) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}