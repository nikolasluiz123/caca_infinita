package br.com.schmittsolucoes.cacasobmedida.domain.usecase

import br.com.schmittsolucoes.cacasobmedida.domain.repository.WordRepository
import kotlinx.coroutines.flow.Flow

class GetCountWordsUseCase(
    private val wordRepository: WordRepository
) {
    fun allWordsObservable(puzzleId: String): Flow<Long> {
        return wordRepository.getCountWordsObservable(puzzleId)
    }

    fun foundWordsObservable(puzzleId: String): Flow<Long> {
        return wordRepository.getCountFoundWordsObservable(puzzleId)
    }
}