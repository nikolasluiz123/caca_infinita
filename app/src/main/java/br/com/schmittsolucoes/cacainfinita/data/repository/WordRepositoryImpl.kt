package br.com.schmittsolucoes.cacainfinita.data.repository

import br.com.schmittsolucoes.cacainfinita.data.database.access.puzzle.word.WordLocalDataSource
import br.com.schmittsolucoes.cacainfinita.data.repository.mapper.toDomain
import br.com.schmittsolucoes.cacainfinita.data.repository.mapper.toEntity
import br.com.schmittsolucoes.cacainfinita.domain.model.Word
import br.com.schmittsolucoes.cacainfinita.domain.repository.WordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WordRepositoryImpl @Inject constructor(
    private val wordLocalDataSource: WordLocalDataSource
): WordRepository {
    override suspend fun updateWord(word: Word) {
        wordLocalDataSource.update(listOf(word.toEntity()))
    }

    override suspend fun getCountWords(puzzleId: String): Long {
        return wordLocalDataSource.selectCountWords(puzzleId)
    }

    override suspend fun getAllWordsBy(puzzleId: String): List<Word> {
        return wordLocalDataSource.selectAllBy(puzzleId).map { it.toDomain() }
    }

    override fun getCountWordsObservable(puzzleId: String): Flow<Long> {
        return wordLocalDataSource.selectCountWordsObservable(puzzleId)
    }

    override fun getCountFoundWordsObservable(puzzleId: String): Flow<Long> {
        return wordLocalDataSource.selectCountFoundWordsObservable(puzzleId)
    }

    override fun hasWordsToSearchObservable(puzzleId: String): Flow<Boolean> {
        return wordLocalDataSource.selectHasWordsToSearchObservable(puzzleId)
    }

    override fun getAllWordsObservable(puzzleId: String): Flow<List<Word>> {
        return wordLocalDataSource.selectAllObservable(puzzleId).map { entities ->
            entities.map { it.toDomain() }
        }
    }
}
