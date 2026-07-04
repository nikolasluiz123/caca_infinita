package br.com.schmittsolucoes.cacasobmedida.domain.usecase

import br.com.schmittsolucoes.cacasobmedida.domain.repository.WordRepository
import kotlinx.coroutines.flow.Flow

class GetHasWordsToSearchUseCase(
    private val wordRepository: WordRepository
) {
    operator fun invoke(puzzleId: String): Flow<Boolean> {
        return wordRepository.hasWordsToSearchObservable(puzzleId)
    }
}
