package br.com.schmittsolucoes.cacasobmedida.domain.usecase

import br.com.schmittsolucoes.cacasobmedida.domain.model.Word
import br.com.schmittsolucoes.cacasobmedida.domain.repository.WordRepository

class GetWordsFromPuzzleUseCase(
    private val wordRepository: WordRepository
) {
    suspend operator fun invoke(puzzleId: String): List<Word> {
        return wordRepository.getAllWordsBy(puzzleId)
    }
}