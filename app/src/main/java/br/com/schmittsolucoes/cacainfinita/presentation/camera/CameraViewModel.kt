package br.com.schmittsolucoes.cacainfinita.presentation.camera

import android.net.Uri
import androidx.camera.core.ImageProxy
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import br.com.schmittsolucoes.cacainfinita.R
import br.com.schmittsolucoes.cacainfinita.data.analyzer.frame.FrameAnalyzer
import br.com.schmittsolucoes.cacainfinita.data.analyzer.frame.ImageProxyFrame
import br.com.schmittsolucoes.cacainfinita.domain.exception.NoPuzzlesGeneratedException
import br.com.schmittsolucoes.cacainfinita.domain.exception.NoTextFoundException
import br.com.schmittsolucoes.cacainfinita.domain.exception.NoValidWordsException
import br.com.schmittsolucoes.cacainfinita.domain.manager.ExceptionRecorderManager
import br.com.schmittsolucoes.cacainfinita.domain.manager.LoadingManager
import br.com.schmittsolucoes.cacainfinita.domain.manager.SnackbarManager
import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.AnalyzerState
import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.TorchMode
import br.com.schmittsolucoes.cacainfinita.domain.model.result.puzzle.PuzzleGenerationConfig
import br.com.schmittsolucoes.cacainfinita.domain.usecase.ImagePuzzleOrchestratorUseCase
import br.com.schmittsolucoes.cacainfinita.presentation.CommonViewModel
import br.com.schmittsolucoes.cacainfinita.presentation.analytics.AnalyticsManager
import br.com.schmittsolucoes.cacainfinita.presentation.camera.navigation.CameraRoute
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val frameAnalyzer: FrameAnalyzer,
    private val imagePuzzleOrchestratorUseCase: ImagePuzzleOrchestratorUseCase,
    private val loadingManager: LoadingManager,
    private val snackbarManager: SnackbarManager,
    private val application: android.app.Application,
    private val analyticsManager: AnalyticsManager,
    exceptionRecorderManager: ExceptionRecorderManager,
    savedStateHandle: SavedStateHandle,
) : CommonViewModel(exceptionRecorderManager) {

    private val navArgs = savedStateHandle.toRoute<CameraRoute>()

    private val _errorMessage = MutableStateFlow<String?>(null)
    private val _torchMode = MutableStateFlow(TorchMode.AUTO)
    private var lastTorchActive = false

    val uiState: StateFlow<CameraUiState> = combine(
        frameAnalyzer.state,
        loadingManager.isLoading,
        _errorMessage,
        _torchMode
    ) { analysisResult, isProcessing, errorMessage, torchMode ->
        val isTorchActive = when (torchMode) {
            TorchMode.AUTO -> {
                val luminosity = analysisResult.luminosity ?: 100f
                if (!lastTorchActive && luminosity < 60f) true
                else lastTorchActive
            }
            TorchMode.ON -> true
            TorchMode.OFF -> false
        }
        lastTorchActive = isTorchActive

        CameraUiState(
            analyzerState = analysisResult.state,
            detectedLines = analysisResult.lines,
            sourceDimensions = analysisResult.sourceDimensions,
            isCaptureButtonEnabled = analysisResult.state == AnalyzerState.ALIGNED,
            isProcessing = isProcessing,
            errorMessage = errorMessage,
            torchMode = torchMode,
            isTorchActive = isTorchActive
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000),
        initialValue = CameraUiState()
    )

    fun onFrameAnalyzed(imageProxy: ImageProxy) {
        frameAnalyzer.analyze(ImageProxyFrame(imageProxy))
    }

    fun onPhotoCaptured(path: String) {
        launch {
            loadingManager.showLoading()

            try {
                val uri = Uri.fromFile(File(path))
                val config = PuzzleGenerationConfig(
                    languageSelection = navArgs.languageSelection,
                    orientation = navArgs.orientation
                )
                imagePuzzleOrchestratorUseCase(listOf(uri), config, isFromCamera = true)
                snackbarManager.showSnackbar(application.getString(R.string.success_puzzle_generated))
                lastTorchActive = false
            } finally {
                loadingManager.hideLoading()
            }
        }
    }

    override fun getErrorMessageFrom(throwable: Throwable): String {
        return when (throwable) {
            is NoTextFoundException -> application.getString(R.string.error_no_text_found)
            is NoValidWordsException -> application.getString(R.string.error_no_valid_words_found)
            is NoPuzzlesGeneratedException -> application.getString(R.string.error_no_puzzles_generated)
            else -> application.getString(R.string.error_unexpected)
        }
    }

    override fun onShowErrorDialog(message: String) {
        _errorMessage.value = message
    }

    fun onDismissErrorDialog() {
        _errorMessage.value = null
    }

    fun onToggleTorchMode() {
        _torchMode.value = when (_torchMode.value) {
            TorchMode.AUTO -> TorchMode.ON
            TorchMode.ON -> TorchMode.OFF
            TorchMode.OFF -> {
                lastTorchActive = false
                TorchMode.AUTO
            }
        }
    }

    fun onBackButtonClick() {
        analyticsManager.logButtonClick(
            buttonName = CameraAnalytics.BACK_BUTTON,
            buttonAction = CameraAnalytics.ACTION_NAVIGATE_BACK
        )
    }

    override fun onCleared() {
        frameAnalyzer.close()
    }
}
