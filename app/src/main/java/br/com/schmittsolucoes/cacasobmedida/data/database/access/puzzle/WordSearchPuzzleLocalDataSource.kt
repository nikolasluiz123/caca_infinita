package br.com.schmittsolucoes.cacasobmedida.data.database.access.puzzle

import androidx.paging.PagingSource
import br.com.schmittsolucoes.cacasobmedida.data.database.access.EntityLocalDataSource
import br.com.schmittsolucoes.cacasobmedida.data.model.WordSearchPuzzleEntity
import br.com.schmittsolucoes.cacasobmedida.data.model.WordSearchPuzzleWithStats
import br.com.schmittsolucoes.cacasobmedida.domain.model.PuzzleRecord
import kotlinx.coroutines.flow.Flow

interface WordSearchPuzzleLocalDataSource: EntityLocalDataSource<WordSearchPuzzleEntity> {
    suspend fun selectById(id: String): WordSearchPuzzleEntity
    fun selectAll(): PagingSource<Int, WordSearchPuzzleWithStats>
    fun selectLastUnfinished(): Flow<String?>
    fun selectRecords(): Flow<List<PuzzleRecord>>
    suspend fun selectCount(): Long
}