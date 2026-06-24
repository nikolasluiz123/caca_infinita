package br.com.schmittsolucoes.cacasobmedida.domain.usecase

import br.com.schmittsolucoes.cacasobmedida.domain.model.PuzzleRecord
import br.com.schmittsolucoes.cacasobmedida.domain.repository.WordSearchPuzzleRepository
import kotlinx.coroutines.flow.Flow

class GetRecordPuzzlesUseCase(
    private val wordSearchPuzzleRepository: WordSearchPuzzleRepository
) {
    operator fun invoke(): Flow<List<PuzzleRecord>> {
        return wordSearchPuzzleRepository.getRecords()
    }
}