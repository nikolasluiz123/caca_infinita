package br.com.schmittsolucoes.cacainfinita.presentation.puzzles

import android.content.Context
import android.net.Uri
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import br.com.schmittsolucoes.cacainfinita.R
import br.com.schmittsolucoes.cacainfinita.domain.model.pagination.PaginationConfig
import br.com.schmittsolucoes.cacainfinita.domain.manager.LoadingManager
import br.com.schmittsolucoes.cacainfinita.domain.usecase.GenerateImagePuzzleUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.GeneratePDFPuzzleUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.GetAllPuzzlesUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.SaveGeneratedPuzzlesUseCase
import br.com.schmittsolucoes.cacainfinita.presentation.CommonViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class WordSearchViewModel @Inject constructor(
    private val generatePdfPuzzleUseCase: GeneratePDFPuzzleUseCase,
    private val generateImagePuzzleUseCase: GenerateImagePuzzleUseCase,
    private val saveGeneratedPuzzlesUseCase: SaveGeneratedPuzzlesUseCase,
    private val loadingManager: LoadingManager,
    @param:ApplicationContext private val context: Context,
    getAllPuzzlesUseCase: GetAllPuzzlesUseCase
) : CommonViewModel() {

    private val _errorMessage = MutableStateFlow<String?>(null)
    private val _puzzles = getAllPuzzlesUseCase(PaginationConfig(pageSize = 100)).cachedIn(viewModelScope)

    val uiState: StateFlow<WordSearchUiState> = combine(
        loadingManager.isLoading,
        _errorMessage
    ) { isLoading, errorMessage ->
        WordSearchUiState(
            puzzles = _puzzles,
            isLoading = isLoading,
            errorMessage = errorMessage
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000),
        initialValue = WordSearchUiState(puzzles = _puzzles)
    )

    fun onPdfsSelected(uris: List<Uri>) {
        if (uris.isEmpty()) return

        launch {
            loadingManager.showLoading()

            try {
                val puzzles = generatePdfPuzzleUseCase(uris)
                saveGeneratedPuzzlesUseCase(puzzles)
            } finally {
                loadingManager.hideLoading()
            }
        }
    }

    fun onImagesSelected(uris: List<Uri>) {
        if (uris.isEmpty()) return

        launch {
            loadingManager.showLoading()

            try {
                val puzzles = generateImagePuzzleUseCase(uris, isFromCamera = false)
                saveGeneratedPuzzlesUseCase(puzzles)
            } finally {
                loadingManager.hideLoading()
            }
        }
    }

    override fun getErrorMessageFrom(throwable: Throwable): String {
        return context.getString(R.string.error_unexpected)
    }

    override fun onShowErrorDialog(message: String) {
        _errorMessage.value = message
    }

    fun onDismissErrorDialog() {
        _errorMessage.value = null
    }
}
