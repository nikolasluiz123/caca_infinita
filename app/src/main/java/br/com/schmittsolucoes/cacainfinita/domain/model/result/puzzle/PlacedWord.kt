package br.com.schmittsolucoes.cacainfinita.domain.model.result.puzzle

import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.Direction
import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.Language

data class PlacedWord(
    val text: String,
    val startCoordinate: Coordinate,
    val direction: Direction,
    val language: Language
)
