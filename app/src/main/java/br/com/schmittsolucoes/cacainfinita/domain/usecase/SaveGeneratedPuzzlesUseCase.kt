package br.com.schmittsolucoes.cacainfinita.domain.usecase

import br.com.schmittsolucoes.cacainfinita.domain.model.result.puzzle.PuzzleResult
import br.com.schmittsolucoes.cacainfinita.domain.repository.WordSearchPuzzleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SaveGeneratedPuzzlesUseCase(
    private val wordSearchPuzzleRepository: WordSearchPuzzleRepository,
) {
    suspend operator fun invoke(puzzles: List<PuzzleResult>) = withContext(Dispatchers.IO) {
        wordSearchPuzzleRepository.insert(puzzles)
    }
}