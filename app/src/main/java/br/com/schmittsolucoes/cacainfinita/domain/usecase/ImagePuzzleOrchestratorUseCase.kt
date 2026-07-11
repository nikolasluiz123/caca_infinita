package br.com.schmittsolucoes.cacainfinita.domain.usecase

import android.net.Uri
import br.com.schmittsolucoes.cacainfinita.domain.manager.FileManager
import br.com.schmittsolucoes.cacainfinita.domain.model.result.puzzle.PuzzleGenerationConfig
import br.com.schmittsolucoes.cacainfinita.domain.model.result.puzzle.PuzzleResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class ImagePuzzleOrchestratorUseCase @Inject constructor(
    private val languageIdentifierUseCase: LanguageIdentifierUseCase<File>,
    private val generateImagePuzzleUseCase: GenerateImagePuzzleUseCase,
    private val saveGeneratedPuzzlesUseCase: SaveGeneratedPuzzlesUseCase,
    private val fileManager: FileManager
) {
    suspend operator fun invoke(
        uris: List<Uri>,
        config: PuzzleGenerationConfig,
        isFromCamera: Boolean
    ): List<PuzzleResult> = withContext(Dispatchers.IO) {
        val files = if (isFromCamera) {
            uris.map { File(it.path!!) }
        } else {
            uris.mapNotNull { fileManager.copyUriToTempFile(it) }
        }

        try {
            val results = files.flatMap { file ->
                val languageResult = languageIdentifierUseCase(file)
                generateImagePuzzleUseCase(languageResult, config)
            }
            saveGeneratedPuzzlesUseCase(results)
            results
        } finally {
            files.forEach { fileManager.deleteFile(it) }
        }
    }
}
