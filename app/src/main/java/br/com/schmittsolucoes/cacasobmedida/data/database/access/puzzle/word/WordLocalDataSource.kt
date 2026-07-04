package br.com.schmittsolucoes.cacasobmedida.data.database.access.puzzle.word

import br.com.schmittsolucoes.cacasobmedida.data.database.access.EntityLocalDataSource
import br.com.schmittsolucoes.cacasobmedida.data.model.WordEntity
import kotlinx.coroutines.flow.Flow

interface WordLocalDataSource: EntityLocalDataSource<WordEntity> {
    suspend fun selectCountWords(puzzleId: String): Long
    suspend fun selectAllBy(puzzleId: String): List<WordEntity>

    fun selectCountWordsObservable(puzzleId: String): Flow<Long>
    fun selectCountFoundWordsObservable(puzzleId: String): Flow<Long>
    fun selectAllObservable(puzzleId: String): Flow<List<WordEntity>>
}