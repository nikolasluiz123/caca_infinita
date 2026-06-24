package br.com.schmittsolucoes.cacasobmedida.domain.model.result.text

import br.com.schmittsolucoes.cacasobmedida.domain.model.BoundingBox

data class TextElement(
    val text: String,
    val boundingBox: BoundingBox?,
    val confidence: Float?,
    val symbols: List<TextSymbol>
)