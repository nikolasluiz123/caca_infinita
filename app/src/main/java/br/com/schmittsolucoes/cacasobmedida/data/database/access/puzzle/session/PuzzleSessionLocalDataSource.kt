package br.com.schmittsolucoes.cacasobmedida.data.database.access.puzzle.session

import br.com.schmittsolucoes.cacasobmedida.data.database.access.EntityLocalDataSource
import br.com.schmittsolucoes.cacasobmedida.data.model.PuzzleSessionEntity
import kotlinx.coroutines.flow.Flow

interface PuzzleSessionLocalDataSource: EntityLocalDataSource<PuzzleSessionEntity> {
    fun selectAllBy(puzzleId: String): Flow<List<PuzzleSessionEntity>>
    suspend fun selectActualSessionBy(puzzleId: String): PuzzleSessionEntity?
}