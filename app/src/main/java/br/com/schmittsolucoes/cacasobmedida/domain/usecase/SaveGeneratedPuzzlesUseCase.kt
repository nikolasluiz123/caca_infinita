package br.com.schmittsolucoes.cacasobmedida.domain.usecase

import br.com.schmittsolucoes.cacasobmedida.domain.generator.PuzzleNameGenerator
import br.com.schmittsolucoes.cacasobmedida.domain.model.result.puzzle.PuzzleResult
import br.com.schmittsolucoes.cacasobmedida.domain.repository.WordSearchPuzzleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SaveGeneratedPuzzlesUseCase(
    private val wordSearchPuzzleRepository: WordSearchPuzzleRepository,
    private val puzzleNameGenerator: PuzzleNameGenerator,
) {
    suspend operator fun invoke(puzzles: List<PuzzleResult>) = withContext(Dispatchers.IO) {
        val updatedPuzzles = puzzleNameGenerator.generate(puzzles)
        wordSearchPuzzleRepository.insert(updatedPuzzles)
    }
}