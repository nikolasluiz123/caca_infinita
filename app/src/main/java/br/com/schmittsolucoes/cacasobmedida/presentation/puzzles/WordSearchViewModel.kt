package br.com.schmittsolucoes.cacasobmedida.presentation.puzzles

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.GenerateImagePuzzleUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.GeneratePDFPuzzleUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.SaveGeneratedPuzzlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class WordSearchViewModel @Inject constructor(
    private val generatePdfPuzzleUseCase: GeneratePDFPuzzleUseCase,
    private val generateImagePuzzleUseCase: GenerateImagePuzzleUseCase,
    private val saveGeneratedPuzzlesUseCase: SaveGeneratedPuzzlesUseCase,
    @param:ApplicationContext private val context: Context,
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

    fun onImagesSelected(uris: List<Uri>) {
        if (uris.isEmpty()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                uris.forEach { uri ->
                    val file = copyUriToTempFile(uri)
                    if (file != null) {
                        val puzzles = generateImagePuzzleUseCase(file)
                        saveGeneratedPuzzlesUseCase(puzzles)
                        file.delete()
                    }
                }
            } catch (e: Exception) {
                Log.e("DEBUG_PROCESS", e.message, e)
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private suspend fun copyUriToTempFile(uri: Uri): File? = withContext(Dispatchers.IO) {
        try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return@withContext null
            val file = File(context.cacheDir, "${System.currentTimeMillis()}.jpg")
            val outputStream = FileOutputStream(file)

            inputStream.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }

            file
        } catch (e: Exception) {
            Log.e("WordSearchViewModel", "Erro ao copiar URI para arquivo", e)
            null
        }
    }
}
