package br.com.schmittsolucoes.cacainfinita.data.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class FullPuzzleDTO(
    val puzzle: WordSearchPuzzleDTO,
    val words: List<WordDTO>,
    val sessions: List<PuzzleSessionDTO>
)
