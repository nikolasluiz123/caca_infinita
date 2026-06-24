package br.com.schmittsolucoes.cacasobmedida.domain.model.enumeration

enum class Direction(val rowStep: Int, val colStep: Int) {
    HORIZONTAL(0, 1),
    VERTICAL(1, 0),
    DIAGONAL_DOWN_RIGHT(1, 1),
    DIAGONAL_UP_RIGHT(-1, 1)
}