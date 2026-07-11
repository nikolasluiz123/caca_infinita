package br.com.schmittsolucoes.cacainfinita.domain.model

import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.GridOrientation
import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.Language

data class WordSearchPuzzleSummary(
    val id: String,
    val name: String,
    val wordsCount: Int,
    val hasUnfinishedWords: Boolean,
    val languages: List<Language>,
    val orientation: GridOrientation
)
