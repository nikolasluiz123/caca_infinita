package br.com.schmittsolucoes.cacasobmedida.data.model

import androidx.room.Embedded

data class WordSearchPuzzleWithStats(
    @Embedded val puzzle: WordSearchPuzzleEntity,
    val wordsCount: Int,
    val hasUnfinishedWords: Boolean
)
