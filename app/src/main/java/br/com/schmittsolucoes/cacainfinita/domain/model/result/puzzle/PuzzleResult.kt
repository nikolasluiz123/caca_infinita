package br.com.schmittsolucoes.cacainfinita.domain.model.result.puzzle

import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.GridOrientation

data class PuzzleResult(
    val grid: Array<CharArray>,
    val placedWords: List<PlacedWord>,
    val orientation: GridOrientation,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PuzzleResult

        if (!grid.contentDeepEquals(other.grid)) return false
        if (placedWords != other.placedWords) return false
        if (orientation != other.orientation) return false

        return true
    }

    override fun hashCode(): Int {
        var result = grid.contentDeepHashCode()
        result = 31 * result + placedWords.hashCode()
        result = 31 * result + orientation.hashCode()
        return result
    }
}
