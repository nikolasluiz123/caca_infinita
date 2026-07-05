package br.com.schmittsolucoes.cacainfinita.domain.model.result.text

import br.com.schmittsolucoes.cacainfinita.domain.model.BoundingBox

data class TextElement(
    val text: String,
    val boundingBox: BoundingBox?,
    val confidence: Float?,
    val symbols: List<TextSymbol>
)