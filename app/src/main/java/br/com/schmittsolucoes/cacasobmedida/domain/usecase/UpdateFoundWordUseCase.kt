package br.com.schmittsolucoes.cacasobmedida.domain.usecase

import br.com.schmittsolucoes.cacasobmedida.core.database.transaction.DatabaseTransaction
import br.com.schmittsolucoes.cacasobmedida.domain.model.Word
import br.com.schmittsolucoes.cacasobmedida.domain.repository.WordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Instant

class UpdateFoundWordUseCase(
    private val wordRepository: WordRepository,
    private val updateUserExperienceUseCase: UpdateUserExperienceUseCase,
    private val transaction: DatabaseTransaction
) {
    suspend operator fun invoke(word: Word) = withContext(Dispatchers.IO) {
        transaction.run {
            val wordWithDate = word.copy(foundDate = Instant.now())
            wordRepository.updateWord(wordWithDate)
            updateUserExperienceUseCase(wordWithDate)
        }
    }
}