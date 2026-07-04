package br.com.schmittsolucoes.cacasobmedida.data.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import br.com.schmittsolucoes.cacasobmedida.R
import br.com.schmittsolucoes.cacasobmedida.core.database.transaction.DatabaseTransaction
import br.com.schmittsolucoes.cacasobmedida.data.database.access.puzzle.WordSearchPuzzleLocalDataSource
import br.com.schmittsolucoes.cacasobmedida.data.database.access.puzzle.word.WordLocalDataSource
import br.com.schmittsolucoes.cacasobmedida.data.database.access.user.UserLocalDataSource
import br.com.schmittsolucoes.cacasobmedida.data.repository.mapper.toDomain
import br.com.schmittsolucoes.cacasobmedida.data.repository.mapper.toEntity
import br.com.schmittsolucoes.cacasobmedida.domain.model.PuzzleRecord
import br.com.schmittsolucoes.cacasobmedida.domain.model.WordSearchPuzzle
import br.com.schmittsolucoes.cacasobmedida.domain.model.WordSearchPuzzleSummary
import br.com.schmittsolucoes.cacasobmedida.domain.model.pagination.PaginationConfig
import br.com.schmittsolucoes.cacasobmedida.domain.model.result.puzzle.PuzzleResult
import br.com.schmittsolucoes.cacasobmedida.domain.repository.WordSearchPuzzleRepository
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

    override suspend fun getPuzzleBy(id: String): WordSearchPuzzle {
        return wordSearchPuzzleLocalDataSource.selectById(id).toDomain()
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

    override fun getRecords(): Flow<List<PuzzleRecord>> {
        return wordSearchPuzzleLocalDataSource.selectRecords()
    }

    override suspend fun getCount(): Long = withContext(Dispatchers.IO) {
        wordSearchPuzzleLocalDataSource.selectCount()
    }
}
