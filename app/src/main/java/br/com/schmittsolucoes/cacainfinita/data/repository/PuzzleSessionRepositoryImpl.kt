package br.com.schmittsolucoes.cacainfinita.data.repository

import br.com.schmittsolucoes.cacainfinita.data.datasource.local.database.access.puzzle.session.PuzzleSessionLocalDataSource
import br.com.schmittsolucoes.cacainfinita.data.repository.mapper.toDomain
import br.com.schmittsolucoes.cacainfinita.data.repository.mapper.toEntity
import br.com.schmittsolucoes.cacainfinita.domain.model.PuzzleSession
import br.com.schmittsolucoes.cacainfinita.domain.repository.PuzzleSessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import javax.inject.Inject

class PuzzleSessionRepositoryImpl @Inject constructor(
    private val puzzleSessionLocalDataSource: PuzzleSessionLocalDataSource
): PuzzleSessionRepository {
    override suspend fun save(session: PuzzleSession) {
        puzzleSessionLocalDataSource.upsert(listOf(session.toEntity()))
    }

    override fun getAllSessionsBy(puzzleId: String): Flow<List<PuzzleSession>> {
        return puzzleSessionLocalDataSource.selectAllByObservable(puzzleId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getActualSessionBy(puzzleId: String): PuzzleSession? {
        return puzzleSessionLocalDataSource.selectActualSessionBy(puzzleId)?.toDomain()
    }
}
