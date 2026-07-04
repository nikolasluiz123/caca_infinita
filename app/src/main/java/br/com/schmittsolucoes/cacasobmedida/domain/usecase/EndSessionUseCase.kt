package br.com.schmittsolucoes.cacasobmedida.domain.usecase

import br.com.schmittsolucoes.cacasobmedida.domain.repository.PuzzleSessionRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.time.Instant

class EndSessionUseCase(
    private val puzzleSessionRepository: PuzzleSessionRepository
) {
    suspend operator fun invoke(puzzleId: String) = withContext(IO) {
        puzzleSessionRepository.getActualSessionBy(puzzleId)?.let { actualSession ->
            val endedSession = actualSession.copy(endedAt = Instant.now())
            puzzleSessionRepository.save(endedSession)
        }
    }
}
