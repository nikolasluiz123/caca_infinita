package br.com.schmittsolucoes.cacasobmedida.domain.model.result

import br.com.schmittsolucoes.cacasobmedida.domain.model.DetectedLine
import br.com.schmittsolucoes.cacasobmedida.domain.model.ImageDimension
import br.com.schmittsolucoes.cacasobmedida.domain.model.enumeration.AnalyzerState

data class FrameAnalysisResult(
    val state: AnalyzerState = AnalyzerState.NOT_DETECTED,
    val lines: List<DetectedLine> = emptyList(),
    val sourceDimensions: ImageDimension? = null
)