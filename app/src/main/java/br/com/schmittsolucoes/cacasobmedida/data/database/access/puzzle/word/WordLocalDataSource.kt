package br.com.schmittsolucoes.cacasobmedida.data.database.access.puzzle.word

import br.com.schmittsolucoes.cacasobmedida.data.database.access.LocalDataSource
import br.com.schmittsolucoes.cacasobmedida.data.model.WordEntity
import br.com.schmittsolucoes.cacasobmedida.domain.model.Word
import kotlinx.coroutines.flow.Flow

interface WordLocalDataSource: LocalDataSource<WordEntity> {
    suspend fun selectCountWords(puzzleId: String): Long
    suspend fun selectAllBy(puzzleId: String): List<WordEntity>

    fun selectCountWordsObservable(puzzleId: String): Flow<Long>
    fun selectCountFoundWordsObservable(puzzleId: String): Flow<Long>
}