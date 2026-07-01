package br.com.schmittsolucoes.cacasobmedida.presentation.home

import br.com.schmittsolucoes.cacasobmedida.domain.model.PuzzleRecord
import br.com.schmittsolucoes.cacasobmedida.domain.model.User

data class HomeUIState(
    val user: User? = null,
    val records: List<PuzzleRecord> = emptyList(),
    val unfinishedPuzzleId: String? = null,
    val errorMessage: String? = null
)