package br.com.schmittsolucoes.cacasobmedida.presentation.camera

import br.com.schmittsolucoes.cacasobmedida.domain.model.BoundingBox
import br.com.schmittsolucoes.cacasobmedida.domain.model.ImageDimension
import br.com.schmittsolucoes.cacasobmedida.domain.model.enumeration.AnalyzerState

data class CameraUiState(
    val analyzerState: AnalyzerState = AnalyzerState.NOT_DETECTED,
    val boundingBox: BoundingBox? = null,
    val sourceDimensions: ImageDimension? = null,
    val isCaptureButtonEnabled: Boolean = false
)
