package br.com.schmittsolucoes.cacasobmedida.presentation.camera.composables

import androidx.camera.core.ImageProxy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.schmittsolucoes.cacasobmedida.R
import br.com.schmittsolucoes.cacasobmedida.presentation.camera.CameraUiState
import br.com.schmittsolucoes.cacasobmedida.presentation.camera.CameraViewModel
import br.com.schmittsolucoes.cacasobmedida.presentation.components.CameraXPreview
import br.com.schmittsolucoes.cacasobmedida.presentation.components.InteractiveOverlay

@Composable
fun CameraScreen(
    viewModel: CameraViewModel,
    onBackClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    CameraScreen(
        state = state,
        onFrameAnalyzed = viewModel::onFrameAnalyzed,
        onCaptureClick = viewModel::onCaptureClick,
        onBackClick = onBackClick
    )
}

@Composable
fun CameraScreen(
    state: CameraUiState,
    onFrameAnalyzed: (ImageProxy) -> Unit,
    onCaptureClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            CameraXPreview(
                onAnalyzeFrame = onFrameAnalyzed,
                modifier = Modifier.fillMaxSize()
            )

            InteractiveOverlay(
                analyzerState = state.analyzerState,
                detectedLines = state.detectedLines,
                sourceDimensions = state.sourceDimensions,
                modifier = Modifier.fillMaxSize()
            )

            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back_24dp),
                    contentDescription = stringResource(id = R.string.back),
                    tint = Color.White
                )
            }

            Button(
                onClick = onCaptureClick,
                enabled = state.isCaptureButtonEnabled,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp)
                    .size(width = 200.dp, height = 56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                )
            ) {
                Text(text = stringResource(id = R.string.camera_capture))
            }
        }
    }
}
