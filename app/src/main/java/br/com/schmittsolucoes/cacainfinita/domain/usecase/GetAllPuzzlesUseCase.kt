package br.com.schmittsolucoes.cacainfinita.domain.usecase

import androidx.paging.PagingData
import br.com.schmittsolucoes.cacainfinita.domain.model.WordSearchPuzzleSummary
import br.com.schmittsolucoes.cacainfinita.domain.model.pagination.PaginationConfig
import br.com.schmittsolucoes.cacainfinita.domain.repository.WordSearchPuzzleRepository
import kotlinx.coroutines.flow.Flow

class GetAllPuzzlesUseCase(
    private val wordSearchPuzzleRepository: WordSearchPuzzleRepository
) {
    operator fun invoke(config: PaginationConfig): Flow<PagingData<WordSearchPuzzleSummary>> {
        return wordSearchPuzzleRepository.getAllPuzzles(config)
    }
}