package br.com.schmittsolucoes.cacainfinita.domain.model

data class GameProgress(
    val user: User,
    val puzzles: List<FullPuzzle>
)
