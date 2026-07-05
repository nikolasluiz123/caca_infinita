package br.com.schmittsolucoes.cacainfinita.domain.model.result.puzzle

data class PuzzleResult(
    val grid: Array<CharArray>,
    val placedWords: List<PlacedWord>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PuzzleResult

        if (!grid.contentDeepEquals(other.grid)) return false
        if (placedWords != other.placedWords) return false

        return true
    }

    override fun hashCode(): Int {
        var result = grid.contentDeepHashCode()
        result = 31 * result + placedWords.hashCode()
        return result
    }
}
