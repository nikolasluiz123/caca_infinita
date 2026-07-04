package br.com.schmittsolucoes.cacasobmedida.presentation.puzzles

import androidx.paging.PagingData
import br.com.schmittsolucoes.cacasobmedida.domain.model.WordSearchPuzzleSummary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class WordSearchUiState(
    val puzzles: Flow<PagingData<WordSearchPuzzleSummary>> = emptyFlow(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
