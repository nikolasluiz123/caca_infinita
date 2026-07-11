package br.com.schmittsolucoes.cacainfinita.domain.model

import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.GridOrientation

data class WordSearchPuzzle(
    override val id: String,
    val grid: List<List<Char>>,
    val name: String,
    val rows: Int,
    val columns: Int,
    val orientation: GridOrientation,
) : UniqueDomain
