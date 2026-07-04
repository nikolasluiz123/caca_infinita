package br.com.schmittsolucoes.cacasobmedida.domain.usecase

import androidx.paging.PagingData
import br.com.schmittsolucoes.cacasobmedida.domain.model.WordSearchPuzzleSummary
import br.com.schmittsolucoes.cacasobmedida.domain.model.pagination.PaginationConfig
import br.com.schmittsolucoes.cacasobmedida.domain.repository.WordSearchPuzzleRepository
import kotlinx.coroutines.flow.Flow

class GetAllPuzzlesUseCase(
    private val wordSearchPuzzleRepository: WordSearchPuzzleRepository
) {
    operator fun invoke(config: PaginationConfig): Flow<PagingData<WordSearchPuzzleSummary>> {
        return wordSearchPuzzleRepository.getAllPuzzles(config)
    }
}