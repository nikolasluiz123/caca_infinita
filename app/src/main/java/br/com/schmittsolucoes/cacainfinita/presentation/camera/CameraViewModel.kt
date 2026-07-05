package br.com.schmittsolucoes.cacainfinita.presentation.camera

import android.net.Uri
import androidx.camera.core.ImageProxy
import androidx.lifecycle.viewModelScope
import br.com.schmittsolucoes.cacainfinita.R
import br.com.schmittsolucoes.cacainfinita.data.analyzer.frame.FrameAnalyzer
import br.com.schmittsolucoes.cacainfinita.data.analyzer.frame.ImageProxyFrame
import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.AnalyzerState
import br.com.schmittsolucoes.cacainfinita.domain.manager.ExceptionRecorderManager
import br.com.schmittsolucoes.cacainfinita.domain.manager.LoadingManager
import br.com.schmittsolucoes.cacainfinita.domain.usecase.GenerateImagePuzzleUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.SaveGeneratedPuzzlesUseCase
import br.com.schmittsolucoes.cacainfinita.presentation.CommonViewModel
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
    private val generateImagePuzzleUseCase: GenerateImagePuzzleUseCase,
    private val saveGeneratedPuzzlesUseCase: SaveGeneratedPuzzlesUseCase,
    private val loadingManager: LoadingManager,
    private val application: android.app.Application,
    exceptionRecorderManager: ExceptionRecorderManager
) : CommonViewModel(exceptionRecorderManager) {

    private val _errorMessage = MutableStateFlow<String?>(null)

    val uiState: StateFlow<CameraUiState> = combine(
        frameAnalyzer.state,
        loadingManager.isLoading,
        _errorMessage
    ) { analysisResult, isProcessing, errorMessage ->
        CameraUiState(
            analyzerState = analysisResult.state,
            detectedLines = analysisResult.lines,
            sourceDimensions = analysisResult.sourceDimensions,
            isCaptureButtonEnabled = analysisResult.state == AnalyzerState.ALIGNED,
            isProcessing = isProcessing,
            errorMessage = errorMessage
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
                val puzzles = generateImagePuzzleUseCase(listOf(uri), isFromCamera = true)
                saveGeneratedPuzzlesUseCase(puzzles)
            } finally {
                loadingManager.hideLoading()
            }
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

    override fun onCleared() {
        frameAnalyzer.close()
    }
}
