package br.com.schmittsolucoes.cacasobmedida.domain.model.result.text

import br.com.schmittsolucoes.cacasobmedida.domain.model.BoundingBox

data class TextSymbol(
    val text: String,
    val boundingBox: BoundingBox?,
    val confidence: Float?
)