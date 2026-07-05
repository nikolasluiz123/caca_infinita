package br.com.schmittsolucoes.cacainfinita.domain.usecase

import br.com.schmittsolucoes.cacainfinita.domain.repository.WordSearchPuzzleRepository
import kotlinx.coroutines.flow.Flow

class GetLastUnfinishedPuzzleUseCase(
    private val wordSearchPuzzleRepository: WordSearchPuzzleRepository
) {
    operator fun invoke(): Flow<String?> {
        return wordSearchPuzzleRepository.getLastUnfinished()
    }
}