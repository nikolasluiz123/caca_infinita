package br.com.schmittsolucoes.cacainfinita.presentation

import android.app.Application
import br.com.schmittsolucoes.cacainfinita.R
import br.com.schmittsolucoes.cacainfinita.domain.manager.ExceptionRecorderManager
import br.com.schmittsolucoes.cacainfinita.domain.manager.LoadingManager
import br.com.schmittsolucoes.cacainfinita.domain.manager.PDFTextExtractorManager
import br.com.schmittsolucoes.cacainfinita.domain.manager.PlayGamesManager
import br.com.schmittsolucoes.cacainfinita.domain.manager.SnackbarManager
import br.com.schmittsolucoes.cacainfinita.domain.manager.TutorialManager
import br.com.schmittsolucoes.cacainfinita.domain.usecase.CreateUserIfNotExistsUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.SyncCloudProgressUseCase
import br.com.schmittsolucoes.cacainfinita.presentation.analytics.AnalyticsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val application: Application,
    private val analyticsManager: AnalyticsManager,
    private val playGamesManager: PlayGamesManager,
    private val syncCloudProgressUseCase: SyncCloudProgressUseCase,
    private val snackbarManager: SnackbarManager,
    val tutorialManager: TutorialManager,
    createUserIfNotExistsUseCase: CreateUserIfNotExistsUseCase,
    pdfTextExtractorManager: PDFTextExtractorManager,
    loadingManager: LoadingManager,
    exceptionRecorderManager: ExceptionRecorderManager
) : CommonViewModel(exceptionRecorderManager) {

    private val _isInitializing = MutableStateFlow(true)
    val isInitializing = _isInitializing.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    val isLoading = loadingManager.isLoading
    val loadingMessage = loadingManager.message

    val snackbarMessage = snackbarManager.message

    init {
        launch {
//            playGamesManager.initialize()
            pdfTextExtractorManager.initialize(application)

//            syncCloudProgressUseCase()
            createUserIfNotExistsUseCase()

            _isInitializing.value = false
        }
    }

    override fun getErrorMessageFrom(throwable: Throwable): String {
        return application.getString(R.string.error_unexpected)
    }

    override fun onShowErrorDialog(message: String) {
        _errorMessage.value = message
    }

    fun onDismissErrorDialog() {
        _errorMessage.value = null
    }

    fun onDismissSnackbar() {
        snackbarManager.hideSnackbar()
    }

    fun onDismissTutorial() {
        tutorialManager.dismiss()
    }

    fun logBottomNavigation(destiny: String) {
        analyticsManager.logNavigation(
            origin = MainActivityAnalytics.SCREEN_NAME,
            destiny = destiny
        )
    }
}
