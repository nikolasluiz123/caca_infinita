package br.com.schmittsolucoes.cacainfinita.domain.usecase

import android.util.Log
import br.com.schmittsolucoes.cacainfinita.domain.calculator.GridDimensionCalculator
import br.com.schmittsolucoes.cacainfinita.domain.generator.PuzzleGenerator
import br.com.schmittsolucoes.cacainfinita.domain.model.result.language.LanguageAnalyzerResult
import br.com.schmittsolucoes.cacainfinita.domain.model.result.puzzle.PuzzleGenerationConfig
import br.com.schmittsolucoes.cacainfinita.domain.model.result.puzzle.PuzzleResult
import br.com.schmittsolucoes.cacainfinita.domain.processor.pipeline.TextProcessorPipeline
import br.com.schmittsolucoes.cacainfinita.domain.provider.DeviceDimensionsProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class GeneratePuzzleUseCase(
    private val dimensionsProvider: DeviceDimensionsProvider,
    private val gridCalculator: GridDimensionCalculator,
    private val textProcessor: TextProcessorPipeline,
    private val puzzleGenerator: PuzzleGenerator,
) {
    open suspend operator fun invoke(
        result: LanguageAnalyzerResult,
        config: PuzzleGenerationConfig
    ): List<PuzzleResult> {
        return withContext(Dispatchers.Default) {
            val tag = this@GeneratePuzzleUseCase::class.simpleName
            Log.d("DEBUG_PROCESS", "$tag: Iniciando geração de quebra-cabeça")

            val gridDimensions = gridCalculator.calculate(
                availableWidthDp = dimensionsProvider.getAvailableWidth(),
                availableHeightDp = dimensionsProvider.getAvailableHeight(),
                cellTargetSizeDp = dimensionsProvider.getCellSize(),
                paddingStartDp = dimensionsProvider.getPaddingStart(),
                paddingEndDp = dimensionsProvider.getPaddingEnd(),
                paddingTopDp = dimensionsProvider.getPaddingTop(),
                paddingBottomDp = dimensionsProvider.getPaddingBottom()
            )

            Log.d("DEBUG_PROCESS", "$tag: Grid Dimensions calculada: $gridDimensions")

            val words = textProcessor.process(result, config, gridDimensions)

            puzzleGenerator.generate(words, gridDimensions).also {
                Log.d("DEBUG_PROCESS", "$tag: Processamento de quebra-cabeça concluído. Total de puzzles: ${it.size}")
            }
        }
    }
}
