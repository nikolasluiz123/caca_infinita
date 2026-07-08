package br.com.schmittsolucoes.cacainfinita.presentation.components.showcase

data class ShowcaseStep(
    val targetId: String,
    val text: String,
    val shape: ShowcaseShape = ShowcaseShape.Rectangle
)

