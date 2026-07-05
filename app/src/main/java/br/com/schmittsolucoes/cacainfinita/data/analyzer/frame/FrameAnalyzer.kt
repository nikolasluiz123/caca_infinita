package br.com.schmittsolucoes.cacainfinita.data.analyzer.frame

import br.com.schmittsolucoes.cacainfinita.domain.model.result.FrameAnalysisResult
import kotlinx.coroutines.flow.StateFlow

interface FrameAnalyzer: AutoCloseable {
    val state: StateFlow<FrameAnalysisResult>

    fun analyze(frame: Frame)
}