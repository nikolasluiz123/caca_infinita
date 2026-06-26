package br.com.schmittsolucoes.cacasobmedida.data.analyzer

import androidx.camera.core.ImageProxy
import br.com.schmittsolucoes.cacasobmedida.domain.model.result.FrameAnalysisResult
import kotlinx.coroutines.flow.StateFlow

import javax.inject.Inject

class MLKitTextFrameAnalyzer @Inject constructor(): FrameAnalyzer<ImageProxy> {
    override val state: StateFlow<FrameAnalysisResult>
        get() = TODO("Not yet implemented")

    override fun analyze(frame: ImageProxy) {
        TODO("Not yet implemented")
    }
}