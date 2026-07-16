package br.com.schmittsolucoes.cacainfinita.domain.usecase

import br.com.schmittsolucoes.cacainfinita.core.database.transaction.DatabaseTransaction
import br.com.schmittsolucoes.cacainfinita.domain.model.Word
import br.com.schmittsolucoes.cacainfinita.domain.repository.WordRepository
import br.com.schmittsolucoes.cacainfinita.domain.usecase.achievement.UpdateAgilityAchievementUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.achievement.UpdateWordsFoundAchievementUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Instant

class UpdateFoundWordUseCase(
    private val wordRepository: WordRepository,
    private val updateUserExperienceUseCase: UpdateUserExperienceUseCase,
    private val updateAgilityAchievementUseCase: UpdateAgilityAchievementUseCase,
    private val updateWordsFoundAchievementUseCase: UpdateWordsFoundAchievementUseCase,
    private val transaction: DatabaseTransaction
) {
    suspend operator fun invoke(word: Word, elapsedTimeMs: Long): Long = withContext(Dispatchers.IO) {
        transaction.run {
            val wordWithDate = word.copy(foundDate = Instant.now())
            wordRepository.updateWord(wordWithDate)
            updateWordsFoundAchievementUseCase()
            updateAgilityAchievementUseCase(word.puzzleId, elapsedTimeMs)
            updateUserExperienceUseCase(wordWithDate)
        }
    }
}