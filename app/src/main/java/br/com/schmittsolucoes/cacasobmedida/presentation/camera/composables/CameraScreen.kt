package br.com.schmittsolucoes.cacasobmedida.presentation.camera.composables

import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.schmittsolucoes.cacasobmedida.R
import br.com.schmittsolucoes.cacasobmedida.presentation.camera.CameraUiState
import br.com.schmittsolucoes.cacasobmedida.presentation.camera.CameraViewModel
import br.com.schmittsolucoes.cacasobmedida.presentation.components.CameraXPreview
import br.com.schmittsolucoes.cacasobmedida.presentation.components.ErrorDialog
import br.com.schmittsolucoes.cacasobmedida.presentation.components.InteractiveOverlay
import br.com.schmittsolucoes.cacasobmedida.presentation.components.takePhoto
import java.util.concurrent.Executors

@Composable
fun CameraScreen(
    viewModel: CameraViewModel,
    onBackClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }
    val imageCapture = remember { ImageCapture.Builder().build() }

    CameraScreen(
        state = state,
        imageCapture = imageCapture,
        onFrameAnalyzed = viewModel::onFrameAnalyzed,
        onCaptureClick = {
            takePhoto(
                context = context,
                imageCapture = imageCapture,
                executor = cameraExecutor,
                onSuccess = { path ->
                    viewModel.onPhotoCaptured(path)
                },
                onError = {  }
            )
        },
        onBackClick = onBackClick,
        onDismissErrorDialog = viewModel::onDismissErrorDialog
    )
}

@Composable
fun CameraScreen(
    state: CameraUiState,
    imageCapture: ImageCapture,
    onFrameAnalyzed: (ImageProxy) -> Unit,
    onCaptureClick: () -> Unit,
    onBackClick: () -> Unit,
    onDismissErrorDialog: () -> Unit = {}
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
                imageCapture = imageCapture,
                isCapturing = state.isProcessing,
                modifier = Modifier.fillMaxSize()
            )

            if (!state.isProcessing) {
                InteractiveOverlay(
                    analyzerState = state.analyzerState,
                    detectedLines = state.detectedLines,
                    sourceDimensions = state.sourceDimensions,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                )
            }

            IconButton(
                onClick = onBackClick,
                enabled = !state.isProcessing,
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

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp)
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(if (state.isCaptureButtonEnabled && !state.isProcessing) Color.White else Color.White.copy(alpha = 0.4f))
                    .border(4.dp, Color.Gray.copy(alpha = 0.5f), CircleShape)
                    .let {
                        if (state.isCaptureButtonEnabled && !state.isProcessing) {
                            it.background(Color.White)
                        } else {
                            it
                        }
                    }
                    .padding(4.dp)
            ) {
                IconButton(
                    onClick = onCaptureClick,
                    enabled = state.isCaptureButtonEnabled && !state.isProcessing,
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (state.isProcessing) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }
        }

        state.errorMessage?.let { message ->
            ErrorDialog(
                message = message,
                onDismiss = onDismissErrorDialog
            )
        }
    }
}
