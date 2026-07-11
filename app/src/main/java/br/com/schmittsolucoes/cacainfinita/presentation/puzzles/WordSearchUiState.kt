package br.com.schmittsolucoes.cacainfinita.presentation.puzzles

import androidx.paging.PagingData
import br.com.schmittsolucoes.cacainfinita.domain.model.WordSearchPuzzleSummary
import br.com.schmittsolucoes.cacainfinita.domain.model.result.puzzle.PuzzleGenerationConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class WordSearchUiState(
    val puzzles: Flow<PagingData<WordSearchPuzzleSummary>> = emptyFlow(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val initialConfig: PuzzleGenerationConfig? = null
)
