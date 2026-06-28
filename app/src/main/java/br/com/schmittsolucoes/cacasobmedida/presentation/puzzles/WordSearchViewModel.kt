package br.com.schmittsolucoes.cacasobmedida.presentation.puzzles

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.GeneratePDFPuzzleUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.SaveGeneratedPuzzlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordSearchViewModel @Inject constructor(
    private val generatePdfPuzzleUseCase: GeneratePDFPuzzleUseCase,
    private val saveGeneratedPuzzlesUseCase: SaveGeneratedPuzzlesUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(WordSearchUiState())
    val uiState: StateFlow<WordSearchUiState> = _uiState.asStateFlow()

    fun onPdfsSelected(uris: List<Uri>) {
        if (uris.isEmpty()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                uris.forEach { uri ->
                    val puzzles = generatePdfPuzzleUseCase(uri)
                    saveGeneratedPuzzlesUseCase(puzzles)
                }
            } catch (e: Exception) {
                Log.e("DEBUG_PROCESS", e.message, e)
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}
