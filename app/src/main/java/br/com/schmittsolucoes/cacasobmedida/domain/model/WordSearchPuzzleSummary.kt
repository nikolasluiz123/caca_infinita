package br.com.schmittsolucoes.cacasobmedida.domain.model

data class WordSearchPuzzleSummary(
    val id: String,
    val name: String,
    val wordsCount: Int,
    val hasUnfinishedWords: Boolean
)
