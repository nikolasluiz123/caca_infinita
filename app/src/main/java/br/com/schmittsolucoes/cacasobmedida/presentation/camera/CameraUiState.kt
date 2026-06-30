package br.com.schmittsolucoes.cacasobmedida.presentation.camera

import br.com.schmittsolucoes.cacasobmedida.domain.model.DetectedLine
import br.com.schmittsolucoes.cacasobmedida.domain.model.ImageDimension
import br.com.schmittsolucoes.cacasobmedida.domain.model.enumeration.AnalyzerState

data class CameraUiState(
    val analyzerState: AnalyzerState = AnalyzerState.NOT_DETECTED,
    val detectedLines: List<DetectedLine> = emptyList(),
    val sourceDimensions: ImageDimension? = null,
    val isCaptureButtonEnabled: Boolean = false,
    val isProcessing: Boolean = false
)
