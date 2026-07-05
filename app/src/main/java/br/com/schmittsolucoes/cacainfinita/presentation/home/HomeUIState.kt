package br.com.schmittsolucoes.cacainfinita.presentation.home

import br.com.schmittsolucoes.cacainfinita.domain.model.PuzzleRecord
import br.com.schmittsolucoes.cacainfinita.domain.model.User

data class HomeUIState(
    val user: User? = null,
    val records: List<PuzzleRecord> = emptyList(),
    val puzzleIdToPlay: String? = null,
    val isNewGame: Boolean = false,
    val errorMessage: String? = null
)
