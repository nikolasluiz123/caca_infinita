package br.com.schmittsolucoes.cacainfinita.domain.usecase

import br.com.schmittsolucoes.cacainfinita.domain.repository.PuzzleSessionRepository
import br.com.schmittsolucoes.cacainfinita.domain.usecase.achievement.UpdatePuzzleCompletionAchievementUseCase
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.time.Instant

class EndSessionUseCase(
    private val puzzleSessionRepository: PuzzleSessionRepository,
    private val saveProgressToCloudUseCase: SaveProgressToCloudUseCase,
    private val updatePuzzleCompletionAchievementUseCase: UpdatePuzzleCompletionAchievementUseCase
) {
    private val mutex = Mutex()

    suspend operator fun invoke(puzzleId: String, isFinished: Boolean = false) = withContext(IO + NonCancellable) {
        mutex.withLock {
            puzzleSessionRepository.getActualSessionBy(puzzleId)?.let { actualSession ->
                val endedSession = actualSession.copy(endedAt = Instant.now())
                puzzleSessionRepository.save(endedSession)
                
                if (isFinished) {
                    updatePuzzleCompletionAchievementUseCase()
                }

                saveProgressToCloudUseCase()
            }
        }
    }
}
