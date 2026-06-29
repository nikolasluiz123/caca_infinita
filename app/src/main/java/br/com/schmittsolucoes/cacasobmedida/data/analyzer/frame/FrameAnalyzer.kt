package br.com.schmittsolucoes.cacasobmedida.data.analyzer.frame

import br.com.schmittsolucoes.cacasobmedida.domain.model.result.FrameAnalysisResult
import kotlinx.coroutines.flow.StateFlow

interface FrameAnalyzer: AutoCloseable {
    val state: StateFlow<FrameAnalysisResult>

    fun analyze(frame: Frame)
}