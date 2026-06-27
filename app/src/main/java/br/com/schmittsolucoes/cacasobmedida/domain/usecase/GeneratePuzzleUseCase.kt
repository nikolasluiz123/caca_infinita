package br.com.schmittsolucoes.cacasobmedida.domain.usecase

import br.com.schmittsolucoes.cacasobmedida.domain.calculator.GridDimensionCalculator
import br.com.schmittsolucoes.cacasobmedida.domain.generator.PuzzleGenerator
import br.com.schmittsolucoes.cacasobmedida.domain.model.result.puzzle.PuzzleResult
import br.com.schmittsolucoes.cacasobmedida.domain.processor.input.InputProcessor
import br.com.schmittsolucoes.cacasobmedida.domain.processor.pipeline.TextProcessorPipeline
import br.com.schmittsolucoes.cacasobmedida.domain.provider.DeviceDimensionsProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class GeneratePuzzleUseCase<INPUT>(
    private val inputProcessor: InputProcessor<INPUT>,
    private val dimensionsProvider: DeviceDimensionsProvider,
    private val gridCalculator: GridDimensionCalculator,
    private val textProcessor: TextProcessorPipeline,
    private val puzzleGenerator: PuzzleGenerator,
) {
    suspend operator fun invoke(input: INPUT): List<PuzzleResult> = withContext(Dispatchers.IO) {
        val text = inputProcessor.process(input)

        val gridDimensions = gridCalculator.calculate(
            availableWidthDp = dimensionsProvider.getAvailableWidth(),
            availableHeightDp = dimensionsProvider.getAvailableHeight(),
            cellTargetSizeDp = dimensionsProvider.getCellSize(),
            paddingStartDp = dimensionsProvider.getPaddingStart(),
            paddingEndDp = dimensionsProvider.getPaddingEnd(),
            paddingTopDp = dimensionsProvider.getPaddingTop(),
            paddingBottomDp = dimensionsProvider.getPaddingBottom()
        )

        val words = textProcessor.process(text, gridDimensions)

        puzzleGenerator.generate(words, gridDimensions)
    }
}