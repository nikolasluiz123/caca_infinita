package br.com.schmittsolucoes.cacasobmedida.data.analyzer

import br.com.schmittsolucoes.cacasobmedida.domain.model.result.FrameAnalysisResult
import kotlinx.coroutines.flow.StateFlow

interface FrameAnalyzer<T> {
    val state: StateFlow<FrameAnalysisResult>

    fun analyze(frame: T)
}