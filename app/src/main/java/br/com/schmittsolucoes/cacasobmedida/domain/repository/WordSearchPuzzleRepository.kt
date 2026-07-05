package br.com.schmittsolucoes.cacasobmedida.domain.repository

import androidx.paging.PagingData
import br.com.schmittsolucoes.cacasobmedida.domain.model.PuzzleRecord
import br.com.schmittsolucoes.cacasobmedida.domain.model.WordSearchPuzzle
import br.com.schmittsolucoes.cacasobmedida.domain.model.WordSearchPuzzleSummary
import br.com.schmittsolucoes.cacasobmedida.domain.model.pagination.PaginationConfig
import br.com.schmittsolucoes.cacasobmedida.domain.model.result.puzzle.PuzzleResult
import kotlinx.coroutines.flow.Flow

interface WordSearchPuzzleRepository {
    suspend fun insert(result: List<PuzzleResult>)
    suspend fun getPuzzleBy(id: String): WordSearchPuzzle
    fun getAllPuzzles(config: PaginationConfig): Flow<PagingData<WordSearchPuzzleSummary>>
    fun getLastUnfinished(): Flow<String?>
    fun getNextPuzzleToPlay(): Flow<String?>
    fun getRecords(): Flow<List<PuzzleRecord>>
    suspend fun getCount(): Long
}