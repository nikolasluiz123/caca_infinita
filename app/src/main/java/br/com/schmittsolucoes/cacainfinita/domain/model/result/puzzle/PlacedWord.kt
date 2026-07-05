package br.com.schmittsolucoes.cacainfinita.domain.model.result.puzzle

import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.Direction

data class PlacedWord(
    val text: String,
    val startCoordinate: Coordinate,
    val direction: Direction
)
