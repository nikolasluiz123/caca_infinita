package br.com.schmittsolucoes.cacainfinita.domain.usecase

import br.com.schmittsolucoes.cacainfinita.core.database.transaction.DatabaseTransaction
import br.com.schmittsolucoes.cacainfinita.domain.model.Word
import br.com.schmittsolucoes.cacainfinita.domain.repository.WordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Instant

class UpdateFoundWordUseCase(
    private val wordRepository: WordRepository,
    private val updateUserExperienceUseCase: UpdateUserExperienceUseCase,
    private val transaction: DatabaseTransaction
) {
    suspend operator fun invoke(word: Word): Long = withContext(Dispatchers.IO) {
        transaction.run {
            val wordWithDate = word.copy(foundDate = Instant.now())
            wordRepository.updateWord(wordWithDate)
            updateUserExperienceUseCase(wordWithDate)
        }
    }
}