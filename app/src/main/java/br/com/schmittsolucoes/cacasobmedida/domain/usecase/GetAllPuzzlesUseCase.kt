package br.com.schmittsolucoes.cacasobmedida.domain.usecase

import br.com.schmittsolucoes.cacasobmedida.domain.model.WordSearchPuzzle
import br.com.schmittsolucoes.cacasobmedida.domain.repository.WordSearchPuzzleRepository
import kotlinx.coroutines.flow.Flow

class GetAllPuzzlesUseCase(
    private val wordSearchPuzzleRepository: WordSearchPuzzleRepository
) {
    operator fun invoke(): Flow<List<WordSearchPuzzle>> {
        return wordSearchPuzzleRepository.getAllPuzzles()
    }
}