package br.com.schmittsolucoes.cacasobmedida.data.database.access.puzzle

import br.com.schmittsolucoes.cacasobmedida.data.database.access.LocalDataSource
import br.com.schmittsolucoes.cacasobmedida.data.model.WordSearchPuzzleEntity
import br.com.schmittsolucoes.cacasobmedida.domain.model.PuzzleRecord
import kotlinx.coroutines.flow.Flow

interface WordSearchPuzzleLocalDataSource: LocalDataSource<WordSearchPuzzleEntity> {
    suspend fun selectById(id: String): WordSearchPuzzleEntity
    fun selectAll(): Flow<List<WordSearchPuzzleEntity>>
    fun selectLastUnfinished(): Flow<String?>
    fun selectRecords(): Flow<List<PuzzleRecord>>

}