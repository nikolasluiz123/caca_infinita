package br.com.schmittsolucoes.cacainfinita.domain.repository

import br.com.schmittsolucoes.cacainfinita.domain.model.Word
import kotlinx.coroutines.flow.Flow

interface WordRepository {
    suspend fun updateWord(word: Word)
    suspend fun getCountWords(puzzleId: String): Long
    suspend fun getAllWordsBy(puzzleId: String): List<Word>

    fun getCountWordsObservable(puzzleId: String): Flow<Long>
    fun getCountFoundWordsObservable(puzzleId: String): Flow<Long>
    fun hasWordsToSearchObservable(puzzleId: String): Flow<Boolean>
    fun getAllWordsObservable(puzzleId: String): Flow<List<Word>>
}