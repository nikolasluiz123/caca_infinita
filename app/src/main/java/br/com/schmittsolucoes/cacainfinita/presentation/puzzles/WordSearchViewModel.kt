package br.com.schmittsolucoes.cacainfinita.presentation.puzzles

import android.content.Context
import android.net.Uri
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import br.com.schmittsolucoes.cacainfinita.R
import br.com.schmittsolucoes.cacainfinita.domain.exception.NoTextFoundException
import br.com.schmittsolucoes.cacainfinita.domain.exception.NoValidWordsException
import br.com.schmittsolucoes.cacainfinita.domain.manager.ExceptionRecorderManager
import br.com.schmittsolucoes.cacainfinita.domain.manager.LoadingManager
import br.com.schmittsolucoes.cacainfinita.domain.manager.SnackbarManager
import br.com.schmittsolucoes.cacainfinita.domain.manager.TutorialManager
import br.com.schmittsolucoes.cacainfinita.domain.model.pagination.PaginationConfig
import br.com.schmittsolucoes.cacainfinita.domain.usecase.DeleteWordSearchPuzzleUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.GenerateImagePuzzleUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.GeneratePDFPuzzleUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.GetAllPuzzlesUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.SaveGeneratedPuzzlesUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.TutorialUseCase
import br.com.schmittsolucoes.cacainfinita.presentation.CommonViewModel
import br.com.schmittsolucoes.cacainfinita.presentation.analytics.AnalyticsManager
import br.com.schmittsolucoes.cacainfinita.presentation.components.showcase.ShowcaseIds
import br.com.schmittsolucoes.cacainfinita.presentation.components.showcase.ShowcaseStep
import br.com.schmittsolucoes.cacainfinita.presentation.components.showcase.TutorialIds
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
    private val deleteWordSearchPuzzleUseCase: DeleteWordSearchPuzzleUseCase,
    private val loadingManager: LoadingManager,
    private val snackbarManager: SnackbarManager,
    @param:ApplicationContext private val context: Context,
    private val analyticsManager: AnalyticsManager,
    getAllPuzzlesUseCase: GetAllPuzzlesUseCase,
    private val tutorialUseCase: TutorialUseCase,
    private val tutorialManager: TutorialManager,
    exceptionRecorderManager: ExceptionRecorderManager
) : CommonViewModel(exceptionRecorderManager) {

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
                snackbarManager.showSnackbar(context.getString(R.string.success_puzzle_generated))
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
                snackbarManager.showSnackbar(context.getString(R.string.success_puzzle_generated))
            } finally {
                loadingManager.hideLoading()
            }
        }
    }

    override fun getErrorMessageFrom(throwable: Throwable): String {
        return when (throwable) {
            is NoTextFoundException -> context.getString(R.string.error_no_text_found)
            is NoValidWordsException -> context.getString(R.string.error_no_valid_words_found)
            else -> context.getString(R.string.error_unexpected)
        }
    }

    override fun onShowErrorDialog(message: String) {
        _errorMessage.value = message
    }

    fun onDismissErrorDialog() {
        _errorMessage.value = null
    }

    fun onAddWordSearchClick() {
        analyticsManager.logButtonClick(
            buttonName = WordSearchAnalytics.ADD_FAB,
            buttonAction = WordSearchAnalytics.ACTION_OPEN_BOTTOM_SHEET
        )
    }

    fun onPuzzleItemClick() {
        analyticsManager.logCardClick(
            cardName = WordSearchAnalytics.PUZZLE_CARD,
            cardAction = WordSearchAnalytics.ACTION_OPEN_PUZZLE
        )
        analyticsManager.logNavigation(
            origin = WordSearchAnalytics.SCREEN_NAME,
            destiny = WordSearchAnalytics.PUZZLE_DESTINY
        )
    }

    fun onDeletePuzzle(puzzleId: String) {
        launch {
            deleteWordSearchPuzzleUseCase(puzzleId)
        }
    }

    fun onBottomSheetOptionClick(option: String) {
        analyticsManager.logButtonClick(
            buttonName = option,
            buttonAction = WordSearchAnalytics.ACTION_SELECT_OPTION
        )
    }

    fun logNavigationToCamera() {
        analyticsManager.logNavigation(
            origin = WordSearchAnalytics.SCREEN_NAME,
            destiny = WordSearchAnalytics.CAMERA_DESTINY
        )
    }

    fun startTutorialIfNeeded(itemCount: Int) {
        if (tutorialManager.currentSteps.value != null) return

        launch {
            tutorialUseCase.checkAndStartTutorial(
                tutorialId = TutorialIds.WORD_SEARCH_LIST,
                steps = listOf(
                    ShowcaseStep(
                        targetId = ShowcaseIds.ADD_PUZZLE_FAB,
                        text = context.getString(R.string.tutorial_puzzles_add)
                    )
                )
            )

            if (itemCount > 0) {
                tutorialUseCase.checkAndStartTutorial(
                    tutorialId = TutorialIds.WORD_SEARCH_ITEM,
                    steps = listOf(
                        ShowcaseStep(
                            targetId = ShowcaseIds.PUZZLE_ITEM_STATUS,
                            text = context.getString(R.string.tutorial_puzzles_item_status)
                        ),
                        ShowcaseStep(
                            targetId = ShowcaseIds.PUZZLE_ITEM_DELETE,
                            text = context.getString(R.string.tutorial_puzzles_item_delete)
                        )
                    )
                )
            }
        }
    }
}
