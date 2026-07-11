package br.com.schmittsolucoes.cacainfinita.domain.processor.pipeline

import br.com.schmittsolucoes.cacainfinita.domain.model.GridDimensions
import br.com.schmittsolucoes.cacainfinita.domain.model.result.language.IdentifiedWord
import br.com.schmittsolucoes.cacainfinita.domain.model.result.language.LanguageAnalyzerResult
import br.com.schmittsolucoes.cacainfinita.domain.model.result.puzzle.PuzzleGenerationConfig

interface TextProcessorPipeline {
    suspend fun process(
        result: LanguageAnalyzerResult,
        config: PuzzleGenerationConfig,
        gridDimensions: GridDimensions
    ): List<IdentifiedWord>
}
