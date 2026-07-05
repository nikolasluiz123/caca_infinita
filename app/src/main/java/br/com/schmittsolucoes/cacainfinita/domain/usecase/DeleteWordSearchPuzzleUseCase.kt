package br.com.schmittsolucoes.cacainfinita.domain.usecase

import br.com.schmittsolucoes.cacainfinita.domain.repository.WordSearchPuzzleRepository

class DeleteWordSearchPuzzleUseCase(
    private val wordSearchPuzzleRepository: WordSearchPuzzleRepository
) {
    suspend operator fun invoke(id: String) {
        wordSearchPuzzleRepository.delete(id)
    }
}
