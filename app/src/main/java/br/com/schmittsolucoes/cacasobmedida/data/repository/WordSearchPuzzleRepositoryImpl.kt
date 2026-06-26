package br.com.schmittsolucoes.cacasobmedida.data.repository

import br.com.schmittsolucoes.cacasobmedida.data.database.access.puzzle.WordSearchPuzzleLocalDataSource
import br.com.schmittsolucoes.cacasobmedida.data.database.access.puzzle.word.WordLocalDataSource
import br.com.schmittsolucoes.cacasobmedida.data.database.access.user.UserLocalDataSource
import br.com.schmittsolucoes.cacasobmedida.core.database.transaction.DatabaseTransaction
import br.com.schmittsolucoes.cacasobmedida.data.repository.mapper.toDomain
import br.com.schmittsolucoes.cacasobmedida.data.repository.mapper.toEntity
import br.com.schmittsolucoes.cacasobmedida.domain.model.PuzzleRecord
import br.com.schmittsolucoes.cacasobmedida.domain.model.WordSearchPuzzle
import br.com.schmittsolucoes.cacasobmedida.domain.model.result.puzzle.PuzzleResult
import br.com.schmittsolucoes.cacasobmedida.domain.repository.WordSearchPuzzleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import javax.inject.Inject

class WordSearchPuzzleRepositoryImpl @Inject constructor(
    private val wordSearchPuzzleLocalDataSource: WordSearchPuzzleLocalDataSource,
    private val wordLocalDataSource: WordLocalDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val transaction: DatabaseTransaction
) : WordSearchPuzzleRepository {
    override suspend fun insert(result: List<PuzzleResult>) {
        val user = userLocalDataSource.selectFirst()

        val puzzleEntities = result.map { it.toEntity(user.id) }

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

    override fun getAllPuzzles(): Flow<List<WordSearchPuzzle>> {
        return wordSearchPuzzleLocalDataSource.selectAll().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getLastUnfinished(): Flow<String?> {
        return wordSearchPuzzleLocalDataSource.selectLastUnfinished()
    }

    override fun getRecords(): Flow<List<PuzzleRecord>> {
        return wordSearchPuzzleLocalDataSource.selectRecords()
    }
}
