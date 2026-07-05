package br.com.schmittsolucoes.cacainfinita.domain.model.result.text

import br.com.schmittsolucoes.cacainfinita.domain.model.BoundingBox

data class TextBlock(
    val text: String,
    val boundingBox: BoundingBox?,
    val lines: List<TextLine>
)