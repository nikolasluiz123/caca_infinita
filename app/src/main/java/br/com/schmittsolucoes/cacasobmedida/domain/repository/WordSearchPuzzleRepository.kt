package br.com.schmittsolucoes.cacasobmedida.domain.repository

import br.com.schmittsolucoes.cacasobmedida.domain.model.PuzzleRecord
import br.com.schmittsolucoes.cacasobmedida.domain.model.WordSearchPuzzle
import br.com.schmittsolucoes.cacasobmedida.domain.model.result.puzzle.PuzzleResult
import kotlinx.coroutines.flow.Flow

interface WordSearchPuzzleRepository {
    suspend fun insert(result: List<PuzzleResult>)
    suspend fun getPuzzleBy(id: String): WordSearchPuzzle
    fun getAllPuzzles(): Flow<List<WordSearchPuzzle>>
    fun getLastUnfinished(): Flow<String?>
    fun getRecords(): Flow<List<PuzzleRecord>>
    suspend fun getCount(): Long
}