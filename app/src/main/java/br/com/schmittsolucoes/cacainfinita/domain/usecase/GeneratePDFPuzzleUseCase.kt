package br.com.schmittsolucoes.cacainfinita.domain.usecase

import br.com.schmittsolucoes.cacainfinita.core.injection.PDFProcessor
import br.com.schmittsolucoes.cacainfinita.domain.calculator.GridDimensionCalculator
import br.com.schmittsolucoes.cacainfinita.domain.generator.PuzzleGenerator
import br.com.schmittsolucoes.cacainfinita.domain.processor.pipeline.TextProcessorPipeline
import br.com.schmittsolucoes.cacainfinita.domain.provider.DeviceDimensionsProvider
import javax.inject.Inject

class GeneratePDFPuzzleUseCase @Inject constructor(
    dimensionsProvider: DeviceDimensionsProvider,
    gridCalculator: GridDimensionCalculator,
    @PDFProcessor textProcessor: TextProcessorPipeline,
    puzzleGenerator: PuzzleGenerator,
) : GeneratePuzzleUseCase(
    dimensionsProvider = dimensionsProvider,
    gridCalculator = gridCalculator,
    textProcessor = textProcessor,
    puzzleGenerator = puzzleGenerator
)
