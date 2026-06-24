package br.com.schmittsolucoes.cacasobmedida.domain.model.result.text

import br.com.schmittsolucoes.cacasobmedida.domain.model.BoundingBox

data class TextLine(
    val text: String,
    val boundingBox: BoundingBox?,
    val confidence: Float?,
    val elements: List<TextElement>
)