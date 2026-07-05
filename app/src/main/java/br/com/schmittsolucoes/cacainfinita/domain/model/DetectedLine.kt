package br.com.schmittsolucoes.cacainfinita.domain.model

import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.AnalyzerState

data class DetectedLine(
    val boundingBox: BoundingBox,
    val state: AnalyzerState,
    val confidence: Float? = null
)
