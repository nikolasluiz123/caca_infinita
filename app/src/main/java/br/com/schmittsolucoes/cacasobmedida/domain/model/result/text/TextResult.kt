package br.com.schmittsolucoes.cacasobmedida.domain.model.result.text

data class TextResult(
    val text: String,
    val blocks: List<TextBlock>
)