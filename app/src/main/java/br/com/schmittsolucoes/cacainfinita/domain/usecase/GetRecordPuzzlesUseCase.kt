package br.com.schmittsolucoes.cacainfinita.domain.usecase

import br.com.schmittsolucoes.cacainfinita.domain.model.PuzzleRecord
import br.com.schmittsolucoes.cacainfinita.domain.repository.WordSearchPuzzleRepository
import kotlinx.coroutines.flow.Flow

class GetRecordPuzzlesUseCase(
    private val wordSearchPuzzleRepository: WordSearchPuzzleRepository
) {
    operator fun invoke(): Flow<List<PuzzleRecord>> {
        return wordSearchPuzzleRepository.getRecords()
    }
}