package br.com.schmittsolucoes.cacainfinita.domain.model.result.text

import br.com.schmittsolucoes.cacainfinita.domain.model.BoundingBox

data class TextSymbol(
    val text: String,
    val boundingBox: BoundingBox?,
    val confidence: Float?
)