package br.com.schmittsolucoes.cacasobmedida.presentation.puzzle

data class PuzzleUiState(
    val elapsedTime: Long = 0L,
    val formattedTime: String = "00:00:00",
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)
