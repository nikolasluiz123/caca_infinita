package br.com.schmittsolucoes.cacasobmedida.domain.usecase

import br.com.schmittsolucoes.cacasobmedida.domain.model.Word
import br.com.schmittsolucoes.cacasobmedida.domain.repository.WordRepository
import kotlinx.coroutines.flow.Flow

class GetWordsFromPuzzleUseCase(
    private val wordRepository: WordRepository
) {
    suspend fun getAllWords(puzzleId: String): List<Word> {
        return wordRepository.getAllWordsBy(puzzleId)
    }

    fun getAllWordsObservable(puzzleId: String): Flow<List<Word>> {
        return wordRepository.getAllWordsObservable(puzzleId)
    }
}