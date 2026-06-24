package br.com.schmittsolucoes.cacasobmedida.domain.model.result

import br.com.schmittsolucoes.cacasobmedida.domain.model.BoundingBox
import br.com.schmittsolucoes.cacasobmedida.domain.model.ImageDimension
import br.com.schmittsolucoes.cacasobmedida.domain.model.enumeration.AnalyzerState

data class FrameAnalysisResult(
    val state: AnalyzerState = AnalyzerState.NOT_DETECTED,
    val boundingBox: BoundingBox? = null,
    val sourceDimensions: ImageDimension? = null
)