package br.com.schmittsolucoes.cacasobmedida.domain.model

data class WordSearchPuzzle(
    override val id: String,
    val grid: List<List<Char>>,
    val name: String,
    val rows: Int,
    val columns: Int,
) : UniqueDomain
