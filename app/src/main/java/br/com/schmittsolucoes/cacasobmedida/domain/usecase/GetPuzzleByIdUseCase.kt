package br.com.schmittsolucoes.cacasobmedida.domain.usecase

import br.com.schmittsolucoes.cacasobmedida.domain.model.WordSearchPuzzle
import br.com.schmittsolucoes.cacasobmedida.domain.repository.WordSearchPuzzleRepository

class GetPuzzleByIdUseCase(
    private val wordSearchPuzzleRepository: WordSearchPuzzleRepository
) {
    suspend operator fun invoke(id: String): WordSearchPuzzle {
        return wordSearchPuzzleRepository.getPuzzleBy(id)
    }
}