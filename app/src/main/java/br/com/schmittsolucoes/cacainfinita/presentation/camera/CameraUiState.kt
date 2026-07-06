package br.com.schmittsolucoes.cacainfinita.presentation.camera

import br.com.schmittsolucoes.cacainfinita.domain.model.DetectedLine
import br.com.schmittsolucoes.cacainfinita.domain.model.ImageDimension
import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.AnalyzerState
import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.TorchMode

data class CameraUiState(
    val analyzerState: AnalyzerState = AnalyzerState.NOT_DETECTED,
    val detectedLines: List<DetectedLine> = emptyList(),
    val sourceDimensions: ImageDimension? = null,
    val isCaptureButtonEnabled: Boolean = false,
    val isProcessing: Boolean = false,
    val errorMessage: String? = null,
    val torchMode: TorchMode = TorchMode.AUTO,
    val isTorchActive: Boolean = false
)
