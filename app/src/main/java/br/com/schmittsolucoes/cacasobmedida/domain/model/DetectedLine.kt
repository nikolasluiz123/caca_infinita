package br.com.schmittsolucoes.cacasobmedida.domain.model

import br.com.schmittsolucoes.cacasobmedida.domain.model.enumeration.AnalyzerState

data class DetectedLine(
    val boundingBox: BoundingBox,
    val state: AnalyzerState,
    val confidence: Float? = null
)
