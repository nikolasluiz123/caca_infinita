package br.com.schmittsolucoes.cacasobmedida.presentation.puzzle

import br.com.schmittsolucoes.cacasobmedida.domain.model.Word
import br.com.schmittsolucoes.cacasobmedida.domain.model.WordSearchPuzzle

data class XpAnimationState(
    val id: Long,
    val amount: Long
)

data class PuzzleUiState(
    val puzzle: WordSearchPuzzle? = null,
    val elapsedTime: Long = 0L,
    val formattedTime: String = "00:00:00",
    val words: List<Word> = emptyList(),
    val isWordsBottomSheetVisible: Boolean = false,
    val paddingBottom: Float = 0f,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val xpAnimations: List<XpAnimationState> = emptyList(),
    val isPuzzleFinished: Boolean = false
)
