package br.com.schmittsolucoes.cacainfinita.presentation.home

import android.content.Context
import androidx.lifecycle.viewModelScope
import br.com.schmittsolucoes.cacainfinita.R
import br.com.schmittsolucoes.cacainfinita.domain.manager.ExceptionRecorderManager
import br.com.schmittsolucoes.cacainfinita.domain.usecase.GetLastUnfinishedPuzzleUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.GetNextPuzzleToPlayUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.GetRecordPuzzlesUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.GetUserUseCase
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
class HomeViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val analyticsManager: AnalyticsManager,
    getUserUseCase: GetUserUseCase,
    getRecordPuzzlesUseCase: GetRecordPuzzlesUseCase,
    getLastUnfinishedPuzzleUseCase: GetLastUnfinishedPuzzleUseCase,
    getNextPuzzleToPlayUseCase: GetNextPuzzleToPlayUseCase,
    private val tutorialUseCase: TutorialUseCase,
    exceptionRecorderManager: ExceptionRecorderManager
): CommonViewModel(exceptionRecorderManager) {

    private val _errorMessage = MutableStateFlow<String?>(null)

    val uiState: StateFlow<HomeUIState> = combine(
        getUserUseCase.observable(),
        getRecordPuzzlesUseCase(),
        getLastUnfinishedPuzzleUseCase(),
        getNextPuzzleToPlayUseCase(),
        _errorMessage
    ) { user, records, unfinishedPuzzleId, nextPuzzleId, errorMessage ->
        HomeUIState(
            user = user,
            records = records,
            puzzleIdToPlay = unfinishedPuzzleId ?: nextPuzzleId,
            isNewGame = unfinishedPuzzleId == null && nextPuzzleId != null,
            errorMessage = errorMessage
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000),
        initialValue = HomeUIState()
    )

    override fun getErrorMessageFrom(throwable: Throwable): String {
        return context.getString(R.string.error_unexpected)
    }

    override fun onShowErrorDialog(message: String) {
        _errorMessage.value = message
    }

    fun onDismissErrorDialog() {
        _errorMessage.value = null
    }

    fun logNavigationToPuzzle() {
        analyticsManager.logButtonClick(
            buttonName = HomeAnalytics.CONTINUE_GAME_BUTTON,
            buttonAction = HomeAnalytics.ACTION_START_PUZZLE
        )
        analyticsManager.logNavigation(
            origin = HomeAnalytics.SCREEN_NAME,
            destiny = HomeAnalytics.PUZZLE_DESTINY
        )
    }

    fun startTutorialIfNeeded() {
        launch {
            tutorialUseCase.checkAndStartTutorial(
                tutorialId = TutorialIds.HOME,
                steps = listOf(
                    ShowcaseStep(
                        targetId = ShowcaseIds.USER_LEVEL_CARD,
                        text = context.getString(R.string.tutorial_home_level_card)
                    ),
                    ShowcaseStep(
                        targetId = ShowcaseIds.PERSONAL_RECORDS_SECTION,
                        text = context.getString(R.string.tutorial_home_records_section)
                    ),
                    ShowcaseStep(
                        targetId = ShowcaseIds.BOTTOM_NAV_BAR,
                        text = context.getString(R.string.tutorial_home_bottom_nav)
                    )
                )
            )
        }
    }
}
