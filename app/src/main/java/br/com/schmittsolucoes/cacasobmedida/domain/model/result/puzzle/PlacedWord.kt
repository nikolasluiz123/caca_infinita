package br.com.schmittsolucoes.cacasobmedida.domain.model.result.puzzle

import br.com.schmittsolucoes.cacasobmedida.domain.model.enumeration.Direction

data class PlacedWord(
    val text: String,
    val startCoordinate: Coordinate,
    val direction: Direction
)
