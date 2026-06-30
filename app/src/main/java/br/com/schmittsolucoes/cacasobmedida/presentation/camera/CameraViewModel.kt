package br.com.schmittsolucoes.cacasobmedida.presentation.camera

import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.schmittsolucoes.cacasobmedida.data.analyzer.frame.FrameAnalyzer
import br.com.schmittsolucoes.cacasobmedida.data.analyzer.frame.ImageProxyFrame
import br.com.schmittsolucoes.cacasobmedida.domain.model.enumeration.AnalyzerState
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.GenerateImagePuzzleUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.SaveGeneratedPuzzlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val frameAnalyzer: FrameAnalyzer,
    private val generateImagePuzzleUseCase: GenerateImagePuzzleUseCase,
    private val saveGeneratedPuzzlesUseCase: SaveGeneratedPuzzlesUseCase,
) : ViewModel() {

    private val _isProcessing = MutableStateFlow(false)

    val uiState: StateFlow<CameraUiState> = frameAnalyzer.state
        .combine(_isProcessing) { analysisResult, isProcessing ->
            CameraUiState(
                analyzerState = analysisResult.state,
                detectedLines = analysisResult.lines,
                sourceDimensions = analysisResult.sourceDimensions,
                isCaptureButtonEnabled = analysisResult.state == AnalyzerState.ALIGNED,
                isProcessing = isProcessing
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
        viewModelScope.launch {
            _isProcessing.update { true }

            try {
                val file = File(path)
                val puzzles = generateImagePuzzleUseCase(file)
                saveGeneratedPuzzlesUseCase(puzzles)
                
                if (file.exists()) {
                    file.delete()
                }
            } finally {
                _isProcessing.update { false }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        frameAnalyzer.close()
    }
}
