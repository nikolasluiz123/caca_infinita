package br.com.schmittsolucoes.cacainfinita.presentation.puzzle

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import br.com.schmittsolucoes.cacainfinita.R
import br.com.schmittsolucoes.cacainfinita.domain.manager.ExceptionRecorderManager
import br.com.schmittsolucoes.cacainfinita.domain.manager.LoadingManager
import br.com.schmittsolucoes.cacainfinita.domain.manager.SnackbarManager
import br.com.schmittsolucoes.cacainfinita.domain.model.Word
import br.com.schmittsolucoes.cacainfinita.domain.model.WordSearchPuzzle
import br.com.schmittsolucoes.cacainfinita.domain.model.result.puzzle.Coordinate
import br.com.schmittsolucoes.cacainfinita.domain.provider.DeviceDimensionsProvider
import br.com.schmittsolucoes.cacainfinita.domain.usecase.EndSessionUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.GetElapsedTimeUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.GetHasWordsToSearchUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.GetPuzzleByIdUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.GetSelectedWordUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.GetWordsFromPuzzleUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.StartSessionUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.TutorialUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.UpdateFoundWordUseCase
import br.com.schmittsolucoes.cacainfinita.presentation.CommonViewModel
import br.com.schmittsolucoes.cacainfinita.presentation.analytics.AnalyticsManager
import br.com.schmittsolucoes.cacainfinita.presentation.components.showcase.ShowcaseIds
import br.com.schmittsolucoes.cacainfinita.presentation.components.showcase.ShowcaseStep
import br.com.schmittsolucoes.cacainfinita.presentation.components.showcase.TutorialIds
import br.com.schmittsolucoes.cacainfinita.presentation.formatters.formatToClock
import br.com.schmittsolucoes.cacainfinita.presentation.puzzle.navigation.puzzleIdArg
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
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
    private val getHasWordsToSearchUseCase: GetHasWordsToSearchUseCase,
    private val getPuzzleByIdUseCase: GetPuzzleByIdUseCase,
    private val getSelectedWordUseCase: GetSelectedWordUseCase,
    private val updateFoundWordUseCase: UpdateFoundWordUseCase,
    getElapsedTimeUseCase: GetElapsedTimeUseCase,
    getWordsFromPuzzleUseCase: GetWordsFromPuzzleUseCase,
    dimensionsProvider: DeviceDimensionsProvider,
    loadingManager: LoadingManager,
    private val snackbarManager: SnackbarManager,
    private val analyticsManager: AnalyticsManager,
    private val tutorialUseCase: TutorialUseCase,
    exceptionRecorderManager: ExceptionRecorderManager
) : CommonViewModel(exceptionRecorderManager) {

    private val puzzleId: String = checkNotNull(savedStateHandle[puzzleIdArg])

    private val _puzzle = MutableStateFlow<WordSearchPuzzle?>(null)
    private val _errorMessage = MutableStateFlow<String?>(null)
    private val _isWordsBottomSheetVisible = MutableStateFlow(false)
    private val _xpAnimations = MutableStateFlow<List<XpAnimationState>>(emptyList())
    private val _isPuzzleFinished = MutableStateFlow(false)

    private var puzzleJob: Job? = null

    @Suppress("UNCHECKED_CAST")
    val uiState: StateFlow<PuzzleUiState> = combine(
        getElapsedTimeUseCase(puzzleId),
        getWordsFromPuzzleUseCase(puzzleId),
        _isWordsBottomSheetVisible,
        _errorMessage,
        loadingManager.isLoading,
        _puzzle,
        _xpAnimations,
        _isPuzzleFinished
    ) { flows ->
        val elapsed = flows[0] as KotlinDuration
        val words = flows[1] as List<Word>
        val isSheetVisible = flows[2] as Boolean
        val errorMessage = flows[3] as String?
        val isLoading = flows[4] as Boolean
        val puzzle = flows[5] as WordSearchPuzzle?
        val xpAnimations = flows[6] as List<XpAnimationState>
        val isPuzzleFinished = flows[7] as Boolean

        PuzzleUiState(
            puzzle = puzzle,
            elapsedTime = elapsed.inWholeMilliseconds,
            formattedTime = elapsed.formatToClock(forceShowHours = true),
            words = words,
            isWordsBottomSheetVisible = isSheetVisible,
            paddingBottom = dimensionsProvider.getPaddingBottom(),
            errorMessage = errorMessage,
            isLoading = isLoading,
            xpAnimations = xpAnimations,
            isPuzzleFinished = isPuzzleFinished
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000),
        initialValue = PuzzleUiState()
    )

    fun onStart() {
        puzzleJob?.cancel()
        puzzleJob = launch {
            _puzzle.value = getPuzzleByIdUseCase(puzzleId)
            showTutorialIfNeeded()
            startSessionUseCase(puzzleId)

            getHasWordsToSearchUseCase(puzzleId).collect { hasWords ->
                if (!hasWords) {
                    _isPuzzleFinished.value = true
                    snackbarManager.showSnackbar(context.getString(R.string.puzzle_finished_message))
                }
            }
        }
    }

    fun onStop() {
        launch {
            endSessionUseCase(puzzleId)
        }
    }

    fun onWordSelected(start: Coordinate, end: Coordinate) {
        val words = uiState.value.words
        val selectedWord = getSelectedWordUseCase(words, start, end)

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
        if (visible) {
            analyticsManager.logButtonClick(
                buttonName = PuzzleAnalytics.WORDS_FAB,
                buttonAction = PuzzleAnalytics.ACTION_OPEN_WORDS_LIST
            )
        }
        _isWordsBottomSheetVisible.value = visible
    }

    fun logNavigationToHome() {
        analyticsManager.logNavigation(
            origin = PuzzleAnalytics.SCREEN_NAME,
            destiny = PuzzleAnalytics.HOME_DESTINY
        )
    }

    fun onAnimationFinished(id: Long) {
        _xpAnimations.value = _xpAnimations.value.filter { it.id != id }
    }

    private suspend fun showTutorialIfNeeded() {
        tutorialUseCase.checkAndStartTutorial(
            tutorialId = TutorialIds.PUZZLE_GAME,
            steps = listOf(
                ShowcaseStep(
                    targetId = ShowcaseIds.PUZZLE_TIMER,
                    text = context.getString(R.string.tutorial_puzzle_timer)
                ),
                ShowcaseStep(
                    targetId = ShowcaseIds.PUZZLE_GRID,
                    text = context.getString(R.string.tutorial_puzzle_gestures)
                ),
                ShowcaseStep(
                    targetId = ShowcaseIds.PUZZLE_WORDS_FAB,
                    text = context.getString(R.string.tutorial_puzzle_words_list)
                ),
                ShowcaseStep(
                    targetId = ShowcaseIds.PUZZLE_GRID,
                    text = context.getString(R.string.tutorial_puzzle_pause)
                )
            )
        )
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
