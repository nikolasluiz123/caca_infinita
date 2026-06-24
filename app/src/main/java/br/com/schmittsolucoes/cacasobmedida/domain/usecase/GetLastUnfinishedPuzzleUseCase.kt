package br.com.schmittsolucoes.cacasobmedida.domain.usecase

import br.com.schmittsolucoes.cacasobmedida.domain.repository.WordSearchPuzzleRepository
import kotlinx.coroutines.flow.Flow

class GetLastUnfinishedPuzzleUseCase(
    private val wordSearchPuzzleRepository: WordSearchPuzzleRepository
) {
    operator fun invoke(): Flow<String?> {
        return wordSearchPuzzleRepository.getLastUnfinished()
    }
}