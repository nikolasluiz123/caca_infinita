package br.com.schmittsolucoes.cacasobmedida.domain.usecase

import android.net.Uri
import br.com.schmittsolucoes.cacasobmedida.core.injection.PDFProcessor
import br.com.schmittsolucoes.cacasobmedida.domain.calculator.GridDimensionCalculator
import br.com.schmittsolucoes.cacasobmedida.domain.generator.PuzzleGenerator
import br.com.schmittsolucoes.cacasobmedida.domain.processor.input.InputProcessor
import br.com.schmittsolucoes.cacasobmedida.domain.processor.pipeline.TextProcessorPipeline
import br.com.schmittsolucoes.cacasobmedida.domain.provider.DeviceDimensionsProvider
import javax.inject.Inject

class GeneratePDFPuzzleUseCase @Inject constructor(
    @PDFProcessor inputProcessor: InputProcessor<Uri>,
    dimensionsProvider: DeviceDimensionsProvider,
    gridCalculator: GridDimensionCalculator,
    @PDFProcessor textProcessor: TextProcessorPipeline,
    puzzleGenerator: PuzzleGenerator,
) : GeneratePuzzleUseCase<Uri>(
    inputProcessor = inputProcessor,
    dimensionsProvider = dimensionsProvider,
    gridCalculator = gridCalculator,
    textProcessor = textProcessor,
    puzzleGenerator = puzzleGenerator
)
