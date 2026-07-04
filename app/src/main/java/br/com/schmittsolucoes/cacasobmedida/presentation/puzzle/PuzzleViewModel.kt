package br.com.schmittsolucoes.cacasobmedida.presentation.puzzle

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import br.com.schmittsolucoes.cacasobmedida.R
import br.com.schmittsolucoes.cacasobmedida.domain.model.Word
import br.com.schmittsolucoes.cacasobmedida.domain.model.WordSearchPuzzle
import br.com.schmittsolucoes.cacasobmedida.domain.manager.LoadingManager
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.EndSessionUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.GetCountWordsUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.GetElapsedTimeUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.GetPuzzleByIdUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.GetWordsFromPuzzleUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.StartSessionUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.provider.DeviceDimensionsProvider
import br.com.schmittsolucoes.cacasobmedida.presentation.CommonViewModel
import br.com.schmittsolucoes.cacasobmedida.presentation.formatters.formatToClock
import br.com.schmittsolucoes.cacasobmedida.presentation.puzzle.navigation.puzzleIdArg
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.time.Duration as KotlinDuration

@HiltViewModel
class PuzzleViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    @param:ApplicationContext private val context: Context,
    private val startSessionUseCase: StartSessionUseCase,
    private val endSessionUseCase: EndSessionUseCase,
    private val getElapsedTimeUseCase: GetElapsedTimeUseCase,
    private val getCountWordsUseCase: GetCountWordsUseCase,
    private val getWordsFromPuzzleUseCase: GetWordsFromPuzzleUseCase,
    private val getPuzzleByIdUseCase: GetPuzzleByIdUseCase,
    private val dimensionsProvider: DeviceDimensionsProvider,
    private val loadingManager: LoadingManager
) : CommonViewModel() {

    private val puzzleId: String = checkNotNull(savedStateHandle[puzzleIdArg])

    private val _puzzle = MutableStateFlow<WordSearchPuzzle?>(null)
    private val _errorMessage = MutableStateFlow<String?>(null)
    private val _isWordsBottomSheetVisible = MutableStateFlow(false)

    @Suppress("UNCHECKED_CAST")
    val uiState: StateFlow<PuzzleUiState> = combine(
        getElapsedTimeUseCase(puzzleId),
        getCountWordsUseCase.allWordsObservable(puzzleId),
        getCountWordsUseCase.foundWordsObservable(puzzleId),
        getWordsFromPuzzleUseCase.getAllWordsObservable(puzzleId),
        _isWordsBottomSheetVisible,
        _errorMessage,
        loadingManager.isLoading,
        _puzzle
    ) { flows ->
        val elapsed = flows[0] as KotlinDuration
        val totalCount = flows[1] as Long
        val foundCount = flows[2] as Long
        val words = flows[3] as List<Word>
        val isSheetVisible = flows[4] as Boolean
        val errorMessage = flows[5] as String?
        val isLoading = flows[6] as Boolean
        val puzzle = flows[7] as WordSearchPuzzle?

        PuzzleUiState(
            puzzle = puzzle,
            elapsedTime = elapsed.inWholeMilliseconds,
            formattedTime = elapsed.formatToClock(forceShowHours = true),
            totalWordsCount = totalCount,
            foundWordsCount = foundCount,
            words = words,
            isWordsBottomSheetVisible = isSheetVisible,
            paddingBottom = dimensionsProvider.getPaddingBottom(),
            errorMessage = errorMessage,
            isLoading = isLoading
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000),
        initialValue = PuzzleUiState()
    )

    fun onStart() {
        launch {
            _puzzle.value = getPuzzleByIdUseCase(puzzleId)
            startSessionUseCase(puzzleId)
        }
    }

    fun onStop() {
        launch {
            endSessionUseCase(puzzleId)
        }
    }

    fun onDismissErrorDialog() {
        _errorMessage.value = null
    }

    fun onToggleWordsBottomSheet(visible: Boolean) {
        _isWordsBottomSheetVisible.value = visible
    }

    override fun getErrorMessageFrom(throwable: Throwable): String {
        return context.getString(R.string.error_unexpected)
    }

    override fun onShowErrorDialog(message: String) {
        _errorMessage.value = message
    }

    override fun onCleared() {
        onStop()
    }
}
