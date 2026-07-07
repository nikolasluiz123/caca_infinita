package br.com.schmittsolucoes.cacainfinita.presentation.camera.composables

import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import android.content.res.Configuration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.schmittsolucoes.cacainfinita.R
import br.com.schmittsolucoes.cacainfinita.presentation.camera.CameraUiState
import br.com.schmittsolucoes.cacainfinita.presentation.camera.CameraViewModel
import br.com.schmittsolucoes.cacainfinita.presentation.camera.composables.components.CameraXPreview
import br.com.schmittsolucoes.cacainfinita.presentation.camera.composables.components.CaptureButton
import br.com.schmittsolucoes.cacainfinita.presentation.camera.composables.components.FlashToggle
import br.com.schmittsolucoes.cacainfinita.presentation.camera.composables.components.InteractiveOverlay
import br.com.schmittsolucoes.cacainfinita.presentation.camera.composables.components.takePhoto
import br.com.schmittsolucoes.cacainfinita.presentation.components.ErrorDialog
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
        onBackClick = {
            viewModel.onBackButtonClick()
            onBackClick()
        },
        onToggleTorchMode = viewModel::onToggleTorchMode,
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
    onToggleTorchMode: () -> Unit,
    onDismissErrorDialog: () -> Unit = {}
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

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
                isTorchActive = state.isTorchActive,
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

            FlashToggle(
                torchMode = state.torchMode,
                onToggle = onToggleTorchMode,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            )

            CaptureButton(
                onClick = onCaptureClick,
                isEnabled = state.isCaptureButtonEnabled,
                isProcessing = state.isProcessing,
                modifier = Modifier
                    .align(if (isLandscape) Alignment.CenterEnd else Alignment.BottomCenter)
                    .padding(
                        bottom = if (isLandscape) 0.dp else 32.dp,
                        end = if (isLandscape) 32.dp else 0.dp
                    )
            )
        }

        state.errorMessage?.let { message ->
            ErrorDialog(
                message = message,
                onDismiss = onDismissErrorDialog
            )
        }
    }
}