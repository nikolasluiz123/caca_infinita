package br.com.schmittsolucoes.cacainfinita.data.database.access.puzzle

import androidx.paging.PagingSource
import br.com.schmittsolucoes.cacainfinita.data.database.access.EntityLocalDataSource
import br.com.schmittsolucoes.cacainfinita.data.model.WordSearchPuzzleEntity
import br.com.schmittsolucoes.cacainfinita.data.model.WordSearchPuzzleWithStats
import br.com.schmittsolucoes.cacainfinita.domain.model.PuzzleRecord
import kotlinx.coroutines.flow.Flow

interface WordSearchPuzzleLocalDataSource: EntityLocalDataSource<WordSearchPuzzleEntity> {
    suspend fun selectById(id: String): WordSearchPuzzleEntity
    fun selectAll(): PagingSource<Int, WordSearchPuzzleWithStats>
    fun selectLastUnfinished(): Flow<String?>
    fun selectNextPuzzleToPlay(): Flow<String?>
    fun selectRecords(): Flow<List<PuzzleRecord>>
    suspend fun selectCount(): Long
}