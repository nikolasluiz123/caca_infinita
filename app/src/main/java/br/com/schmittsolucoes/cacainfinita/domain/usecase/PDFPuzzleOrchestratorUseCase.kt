package br.com.schmittsolucoes.cacainfinita.domain.usecase

import android.net.Uri
import br.com.schmittsolucoes.cacainfinita.domain.model.result.puzzle.PuzzleGenerationConfig
import br.com.schmittsolucoes.cacainfinita.domain.model.result.puzzle.PuzzleResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PDFPuzzleOrchestratorUseCase @Inject constructor(
    private val languageIdentifierUseCase: LanguageIdentifierUseCase<Uri>,
    private val generatePdfPuzzleUseCase: GeneratePDFPuzzleUseCase,
    private val saveGeneratedPuzzlesUseCase: SaveGeneratedPuzzlesUseCase
) {
    suspend operator fun invoke(
        uris: List<Uri>,
        config: PuzzleGenerationConfig
    ): List<PuzzleResult> = withContext(Dispatchers.Default) {
        val results = uris.flatMap { uri ->
            val languageResult = languageIdentifierUseCase(uri)
            generatePdfPuzzleUseCase(languageResult, config)
        }
        saveGeneratedPuzzlesUseCase(results)
        results
    }
}
