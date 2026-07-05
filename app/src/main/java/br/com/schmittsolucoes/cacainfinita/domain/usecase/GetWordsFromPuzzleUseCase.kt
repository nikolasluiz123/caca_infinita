package br.com.schmittsolucoes.cacainfinita.domain.usecase

import br.com.schmittsolucoes.cacainfinita.domain.model.Word
import br.com.schmittsolucoes.cacainfinita.domain.repository.WordRepository
import kotlinx.coroutines.flow.Flow

class GetWordsFromPuzzleUseCase(
    private val wordRepository: WordRepository
) {
    operator fun invoke(puzzleId: String): Flow<List<Word>> {
        return wordRepository.getAllWordsObservable(puzzleId)
    }
}