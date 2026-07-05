package br.com.schmittsolucoes.cacainfinita.domain.usecase

import br.com.schmittsolucoes.cacainfinita.domain.model.PuzzleSession
import br.com.schmittsolucoes.cacainfinita.domain.repository.PuzzleSessionRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class StartSessionUseCase(
    private val puzzleSessionRepository: PuzzleSessionRepository
) {
    @OptIn(ExperimentalUuidApi::class)
    suspend operator fun invoke(puzzleId: String) = withContext(IO){
        val actualSession = puzzleSessionRepository.getActualSessionBy(puzzleId)

        if (actualSession == null) {
            val newSession = PuzzleSession(
                id = Uuid.random().toString(),
                puzzleId = puzzleId,
                startedAt = Instant.now()
            )

            puzzleSessionRepository.save(newSession)
        }
    }
}
