package br.com.schmittsolucoes.cacasobmedida.presentation.camera

import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.schmittsolucoes.cacasobmedida.data.analyzer.frame.FrameAnalyzer
import br.com.schmittsolucoes.cacasobmedida.data.analyzer.frame.ImageProxyFrame
import br.com.schmittsolucoes.cacasobmedida.domain.model.enumeration.AnalyzerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val frameAnalyzer: FrameAnalyzer
) : ViewModel() {

    val uiState: StateFlow<CameraUiState> = frameAnalyzer.state
        .combine(MutableStateFlow(false)) { analysisResult, _ ->
            CameraUiState(
                analyzerState = analysisResult.state,
                detectedLines = analysisResult.lines,
                sourceDimensions = analysisResult.sourceDimensions,
                isCaptureButtonEnabled = analysisResult.state == AnalyzerState.ALIGNED
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(3000),
            initialValue = CameraUiState()
        )

    fun onFrameAnalyzed(imageProxy: ImageProxy) {
        frameAnalyzer.analyze(ImageProxyFrame(imageProxy))
    }

    fun onCaptureClick() {

    }

    override fun onCleared() {
        super.onCleared()
        frameAnalyzer.close()
    }
}
