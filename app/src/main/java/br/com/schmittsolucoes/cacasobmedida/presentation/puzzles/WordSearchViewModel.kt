package br.com.schmittsolucoes.cacasobmedida.presentation.puzzles

import android.net.Uri
import br.com.schmittsolucoes.cacasobmedida.R
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.GenerateImagePuzzleUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.GeneratePDFPuzzleUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.SaveGeneratedPuzzlesUseCase
import br.com.schmittsolucoes.cacasobmedida.presentation.CommonViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class WordSearchViewModel @Inject constructor(
    private val generatePdfPuzzleUseCase: GeneratePDFPuzzleUseCase,
    private val generateImagePuzzleUseCase: GenerateImagePuzzleUseCase,
    private val saveGeneratedPuzzlesUseCase: SaveGeneratedPuzzlesUseCase,
    private val application: android.app.Application
) : CommonViewModel() {
    private val _uiState = MutableStateFlow(WordSearchUiState())
    val uiState: StateFlow<WordSearchUiState> = _uiState.asStateFlow()

    fun onPdfsSelected(uris: List<Uri>) {
        if (uris.isEmpty()) return

        launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val puzzles = generatePdfPuzzleUseCase(uris)
                saveGeneratedPuzzlesUseCase(puzzles)
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun onImagesSelected(uris: List<Uri>) {
        if (uris.isEmpty()) return

        launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val puzzles = generateImagePuzzleUseCase(uris, isFromCamera = false)
                saveGeneratedPuzzlesUseCase(puzzles)
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    override fun getErrorMessageFrom(throwable: Throwable): String {
        return application.getString(R.string.error_unexpected)
    }

    override fun onShowErrorDialog(message: String) {
        _uiState.update { it.copy(errorMessage = message) }
    }

    fun onDismissErrorDialog() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}
