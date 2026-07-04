package br.com.schmittsolucoes.cacasobmedida.presentation.puzzle

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import br.com.schmittsolucoes.cacasobmedida.R
import br.com.schmittsolucoes.cacasobmedida.domain.manager.LoadingManager
import br.com.schmittsolucoes.cacasobmedida.domain.model.Word
import br.com.schmittsolucoes.cacasobmedida.domain.model.WordSearchPuzzle
import br.com.schmittsolucoes.cacasobmedida.domain.model.result.puzzle.Coordinate
import br.com.schmittsolucoes.cacasobmedida.domain.provider.DeviceDimensionsProvider
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.EndSessionUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.GetCountWordsUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.GetElapsedTimeUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.GetPuzzleByIdUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.GetWordsFromPuzzleUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.StartSessionUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.UpdateFoundWordUseCase
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
    private val updateFoundWordUseCase: UpdateFoundWordUseCase,
    private val dimensionsProvider: DeviceDimensionsProvider,
    private val loadingManager: LoadingManager
) : CommonViewModel() {

    private val puzzleId: String = checkNotNull(savedStateHandle[puzzleIdArg])

    private val _puzzle = MutableStateFlow<WordSearchPuzzle?>(null)
    private val _errorMessage = MutableStateFlow<String?>(null)
    private val _isWordsBottomSheetVisible = MutableStateFlow(false)
    private val _xpAnimations = MutableStateFlow<List<XpAnimationState>>(emptyList())

    @Suppress("UNCHECKED_CAST")
    val uiState: StateFlow<PuzzleUiState> = combine(
        getElapsedTimeUseCase(puzzleId),
        getCountWordsUseCase.allWordsObservable(puzzleId),
        getCountWordsUseCase.foundWordsObservable(puzzleId),
        getWordsFromPuzzleUseCase.getAllWordsObservable(puzzleId),
        _isWordsBottomSheetVisible,
        _errorMessage,
        loadingManager.isLoading,
        _puzzle,
        _xpAnimations
    ) { flows ->
        val elapsed = flows[0] as KotlinDuration
        val totalCount = flows[1] as Long
        val foundCount = flows[2] as Long
        val words = flows[3] as List<Word>
        val isSheetVisible = flows[4] as Boolean
        val errorMessage = flows[5] as String?
        val isLoading = flows[6] as Boolean
        val puzzle = flows[7] as WordSearchPuzzle?
        val xpAnimations = flows[8] as List<XpAnimationState>

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
            isLoading = isLoading,
            xpAnimations = xpAnimations
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

    fun onWordSelected(start: Coordinate, end: Coordinate) {
        val words = uiState.value.words
        val selectedWord = words.find { word ->
            val wordEndRow = word.startRow + (word.text.length - 1) * word.direction.rowStep
            val wordEndCol = word.startCol + (word.text.length - 1) * word.direction.colStep

            (word.startRow == start.row && word.startCol == start.col && wordEndRow == end.row && wordEndCol == end.col) ||
            (word.startRow == end.row && word.startCol == end.col && wordEndRow == start.row && wordEndCol == start.col)
        }

        selectedWord?.let { word ->
            if (word.foundDate == null) {
                launch {
                    val xpGained = updateFoundWordUseCase(word)
                    val newAnimation = XpAnimationState(id = System.currentTimeMillis(), amount = xpGained)
                    _xpAnimations.value += newAnimation
                }
            }
        }
    }

    fun onDismissErrorDialog() {
        _errorMessage.value = null
    }

    fun onToggleWordsBottomSheet(visible: Boolean) {
        _isWordsBottomSheetVisible.value = visible
    }

    fun onAnimationFinished(id: Long) {
        _xpAnimations.value = _xpAnimations.value.filter { it.id != id }
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
