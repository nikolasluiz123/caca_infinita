package br.com.schmittsolucoes.cacainfinita.domain.usecase

import br.com.schmittsolucoes.cacainfinita.domain.model.WordSearchPuzzle
import br.com.schmittsolucoes.cacainfinita.domain.repository.WordSearchPuzzleRepository

class GetPuzzleByIdUseCase(
    private val wordSearchPuzzleRepository: WordSearchPuzzleRepository
) {
    suspend operator fun invoke(id: String): WordSearchPuzzle {
        return wordSearchPuzzleRepository.getPuzzleBy(id)
    }
}