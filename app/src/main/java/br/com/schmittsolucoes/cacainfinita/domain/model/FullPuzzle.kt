package br.com.schmittsolucoes.cacainfinita.domain.model

data class FullPuzzle(
    val puzzle: WordSearchPuzzle,
    val words: List<Word>,
    val sessions: List<PuzzleSession>
)
