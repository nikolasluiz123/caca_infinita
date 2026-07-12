package br.com.schmittsolucoes.cacainfinita.data.datasource.local.database.access.puzzle.session

import br.com.schmittsolucoes.cacainfinita.data.datasource.local.database.access.EntityLocalDataSource
import br.com.schmittsolucoes.cacainfinita.data.model.PuzzleSessionEntity
import kotlinx.coroutines.flow.Flow

interface PuzzleSessionLocalDataSource: EntityLocalDataSource<PuzzleSessionEntity> {
    suspend fun selectAllBy(puzzleId: String): List<PuzzleSessionEntity>
    fun selectAllByObservable(puzzleId: String): Flow<List<PuzzleSessionEntity>>
    suspend fun selectActualSessionBy(puzzleId: String): PuzzleSessionEntity?
}