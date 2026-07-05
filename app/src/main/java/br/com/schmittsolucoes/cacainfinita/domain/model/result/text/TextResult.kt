package br.com.schmittsolucoes.cacainfinita.domain.model.result.text

data class TextResult(
    val text: String,
    val blocks: List<TextBlock>
)