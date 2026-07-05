package br.com.schmittsolucoes.cacainfinita.domain.model.result

import br.com.schmittsolucoes.cacainfinita.domain.model.DetectedLine
import br.com.schmittsolucoes.cacainfinita.domain.model.ImageDimension
import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.AnalyzerState

data class FrameAnalysisResult(
    val state: AnalyzerState = AnalyzerState.NOT_DETECTED,
    val lines: List<DetectedLine> = emptyList(),
    val sourceDimensions: ImageDimension? = null
)