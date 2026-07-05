package br.com.schmittsolucoes.cacainfinita.domain.model.enumeration

enum class Direction(val rowStep: Int, val colStep: Int) {
    HORIZONTAL(0, 1),
    VERTICAL(1, 0),
    DIAGONAL_DOWN_RIGHT(1, 1),
    DIAGONAL_UP_RIGHT(-1, 1),
    HORIZONTAL_REVERSE(0, -1),
    VERTICAL_REVERSE(-1, 0),
    DIAGONAL_DOWN_LEFT(1, -1),
    DIAGONAL_UP_LEFT(-1, -1)
}
