package br.com.schmittsolucoes.cacainfinita.data.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import br.com.schmittsolucoes.cacainfinita.R
import br.com.schmittsolucoes.cacainfinita.core.database.transaction.DatabaseTransaction
import br.com.schmittsolucoes.cacainfinita.data.datasource.local.database.access.puzzle.WordSearchPuzzleLocalDataSource
import br.com.schmittsolucoes.cacainfinita.data.datasource.local.database.access.puzzle.session.PuzzleSessionLocalDataSource
import br.com.schmittsolucoes.cacainfinita.data.datasource.local.database.access.puzzle.word.WordLocalDataSource
import br.com.schmittsolucoes.cacainfinita.data.datasource.local.database.access.user.UserLocalDataSource
import br.com.schmittsolucoes.cacainfinita.data.repository.mapper.toDomain
import br.com.schmittsolucoes.cacainfinita.data.repository.mapper.toEntity
import br.com.schmittsolucoes.cacainfinita.domain.model.FullPuzzle
import br.com.schmittsolucoes.cacainfinita.domain.model.PuzzleRecord
import br.com.schmittsolucoes.cacainfinita.domain.model.WordSearchPuzzle
import br.com.schmittsolucoes.cacainfinita.domain.model.WordSearchPuzzleSummary
import br.com.schmittsolucoes.cacainfinita.domain.model.pagination.PaginationConfig
import br.com.schmittsolucoes.cacainfinita.domain.model.result.puzzle.PuzzleResult
import br.com.schmittsolucoes.cacainfinita.domain.repository.WordSearchPuzzleRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WordSearchPuzzleRepositoryImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val wordSearchPuzzleLocalDataSource: WordSearchPuzzleLocalDataSource,
    private val wordLocalDataSource: WordLocalDataSource,
    private val puzzleSessionLocalDataSource: PuzzleSessionLocalDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val transaction: DatabaseTransaction
) : WordSearchPuzzleRepository {
    override suspend fun insert(result: List<PuzzleResult>) {
        val user = userLocalDataSource.selectFirst()
        val existingPuzzlesCount = getCount()

        val puzzleEntities = result.mapIndexed { index, result ->
            val fallbackName = context.getString(
                R.string.puzzle_fallback_name,
                existingPuzzlesCount + (index + 1)
            )

            result.toEntity(fallbackName, user.id)
        }

        val wordEntities = result.zip(puzzleEntities).flatMap { (puzzleResult, puzzleEntity) ->
            puzzleResult.placedWords.map { it.toEntity(puzzleEntity.id) }
        }

        transaction.run {
            wordSearchPuzzleLocalDataSource.insert(puzzleEntities)
            wordLocalDataSource.insert(wordEntities)
        }
    }

    override suspend fun insertFull(puzzles: List<FullPuzzle>) {
        val userId = userLocalDataSource.selectFirst().id
        val puzzleEntities = puzzles.map { it.puzzle.toEntity(userId) }
        val wordEntities = puzzles.flatMap { it.words.map { word -> word.toEntity() } }
        val sessionEntities = puzzles.flatMap { it.sessions.map { session -> session.toEntity() } }

        wordSearchPuzzleLocalDataSource.upsert(puzzleEntities)
        wordLocalDataSource.upsert(wordEntities)
        puzzleSessionLocalDataSource.upsert(sessionEntities)
    }

    override suspend fun getPuzzleBy(id: String): WordSearchPuzzle {
        return wordSearchPuzzleLocalDataSource.selectById(id).toDomain()
    }

    override suspend fun getFullPuzzlesByIds(ids: List<String>): List<FullPuzzle> {
        return ids.map { id ->
            val puzzle = wordSearchPuzzleLocalDataSource.selectById(id)
            val words = wordLocalDataSource.selectAllBy(id)
            val sessions = puzzleSessionLocalDataSource.selectAllBy(id)

            FullPuzzle(
                puzzle = puzzle.toDomain(),
                words = words.map { it.toDomain() },
                sessions = sessions.map { it.toDomain() }
            )
        }
    }

    override suspend fun getUnfinishedFullPuzzles(): List<FullPuzzle> {
        val entities = wordSearchPuzzleLocalDataSource.selectUnfinishedFull()

        return entities.map { puzzle ->
            val id = puzzle.id
            val words = wordLocalDataSource.selectAllBy(id)
            val sessions = puzzleSessionLocalDataSource.selectAllBy(id)
            FullPuzzle(
                puzzle = puzzle.toDomain(),
                words = words.map { it.toDomain() },
                sessions = sessions.map { it.toDomain() }
            )
        }
    }

    override fun getAllPuzzles(config: PaginationConfig): Flow<PagingData<WordSearchPuzzleSummary>> {
        return Pager(
            config = PagingConfig(
                pageSize = config.pageSize,
                enablePlaceholders = config.enablePlaceholders
            ),
            pagingSourceFactory = { wordSearchPuzzleLocalDataSource.selectAll() }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

    override fun getLastUnfinished(): Flow<String?> {
        return wordSearchPuzzleLocalDataSource.selectLastUnfinished()
    }

    override fun getNextPuzzleToPlay(): Flow<String?> {
        return wordSearchPuzzleLocalDataSource.selectNextPuzzleToPlay()
    }

    override fun getRecords(): Flow<List<PuzzleRecord>> {
        return wordSearchPuzzleLocalDataSource.selectRecords()
    }

    override suspend fun getCount(): Long = withContext(Dispatchers.IO) {
        wordSearchPuzzleLocalDataSource.selectCount()
    }

    override suspend fun delete(id: String) = withContext(Dispatchers.IO) {
        val entity = wordSearchPuzzleLocalDataSource.selectById(id)
        wordSearchPuzzleLocalDataSource.delete(listOf(entity))
    }
}
