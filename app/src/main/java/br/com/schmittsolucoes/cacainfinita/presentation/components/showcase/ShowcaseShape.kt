package br.com.schmittsolucoes.cacainfinita.presentation.components.showcase

sealed class ShowcaseShape {
    object Rectangle : ShowcaseShape()
    object Circle : ShowcaseShape()
}