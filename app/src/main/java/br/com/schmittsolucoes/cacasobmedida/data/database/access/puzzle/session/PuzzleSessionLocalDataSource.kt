package br.com.schmittsolucoes.cacasobmedida.data.database.access.puzzle.session

import br.com.schmittsolucoes.cacasobmedida.data.database.access.LocalDataSource
import br.com.schmittsolucoes.cacasobmedida.data.model.PuzzleSessionEntity
import kotlinx.coroutines.flow.Flow

interface PuzzleSessionLocalDataSource: LocalDataSource<PuzzleSessionEntity> {
    fun selectAllBy(puzzleId: String): Flow<List<PuzzleSessionEntity>>
    suspend fun selectActualSessionBy(puzzleId: String): PuzzleSessionEntity?
}