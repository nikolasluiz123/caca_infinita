package br.com.schmittsolucoes.cacasobmedida.presentation.puzzle

import br.com.schmittsolucoes.cacasobmedida.domain.model.Word

data class PuzzleUiState(
    val elapsedTime: Long = 0L,
    val formattedTime: String = "00:00:00",
    val totalWordsCount: Long = 0,
    val foundWordsCount: Long = 0,
    val words: List<Word> = emptyList(),
    val isWordsBottomSheetVisible: Boolean = false,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)
