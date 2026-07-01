package br.com.schmittsolucoes.cacasobmedida.domain.usecase

import android.net.Uri
import br.com.schmittsolucoes.cacasobmedida.core.injection.ImageProcessor
import br.com.schmittsolucoes.cacasobmedida.domain.calculator.GridDimensionCalculator
import br.com.schmittsolucoes.cacasobmedida.domain.generator.PuzzleGenerator
import br.com.schmittsolucoes.cacasobmedida.domain.manager.FileManager
import br.com.schmittsolucoes.cacasobmedida.domain.model.result.puzzle.PuzzleResult
import br.com.schmittsolucoes.cacasobmedida.domain.processor.input.InputProcessor
import br.com.schmittsolucoes.cacasobmedida.domain.processor.pipeline.TextProcessorPipeline
import br.com.schmittsolucoes.cacasobmedida.domain.provider.DeviceDimensionsProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class GenerateImagePuzzleUseCase @Inject constructor(
    @ImageProcessor inputProcessor: InputProcessor<File>,
    dimensionsProvider: DeviceDimensionsProvider,
    gridCalculator: GridDimensionCalculator,
    @ImageProcessor textProcessor: TextProcessorPipeline,
    puzzleGenerator: PuzzleGenerator,
    private val fileManager: FileManager,
) : GeneratePuzzleUseCase<File>(
    inputProcessor = inputProcessor,
    dimensionsProvider = dimensionsProvider,
    gridCalculator = gridCalculator,
    textProcessor = textProcessor,
    puzzleGenerator = puzzleGenerator
) {
    override suspend fun invoke(inputs: List<File>): List<PuzzleResult> {
        return try {
            super.invoke(inputs)
        } finally {
            withContext(Dispatchers.IO) {
                inputs.forEach { fileManager.deleteFile(it) }
            }
        }
    }

    suspend operator fun invoke(uris: List<Uri>, isFromCamera: Boolean): List<PuzzleResult> {
        return withContext(Dispatchers.IO) {
            val files = if (isFromCamera) {
                uris.map { File(it.path!!) }
            } else {
                uris.mapNotNull { fileManager.copyUriToTempFile(it) }
            }

            invoke(files)
        }
    }
}
