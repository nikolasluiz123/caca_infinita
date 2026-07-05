package br.com.schmittsolucoes.cacainfinita.domain.repository

import androidx.paging.PagingData
import br.com.schmittsolucoes.cacainfinita.domain.model.PuzzleRecord
import br.com.schmittsolucoes.cacainfinita.domain.model.WordSearchPuzzle
import br.com.schmittsolucoes.cacainfinita.domain.model.WordSearchPuzzleSummary
import br.com.schmittsolucoes.cacainfinita.domain.model.pagination.PaginationConfig
import br.com.schmittsolucoes.cacainfinita.domain.model.result.puzzle.PuzzleResult
import kotlinx.coroutines.flow.Flow

interface WordSearchPuzzleRepository {
    suspend fun insert(result: List<PuzzleResult>)
    suspend fun getPuzzleBy(id: String): WordSearchPuzzle
    fun getAllPuzzles(config: PaginationConfig): Flow<PagingData<WordSearchPuzzleSummary>>
    fun getLastUnfinished(): Flow<String?>
    fun getNextPuzzleToPlay(): Flow<String?>
    fun getRecords(): Flow<List<PuzzleRecord>>
    suspend fun getCount(): Long
    suspend fun delete(id: String)
}