package br.com.schmittsolucoes.cacasobmedida.domain.usecase

import br.com.schmittsolucoes.cacasobmedida.domain.repository.PuzzleSessionRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import java.time.Duration
import java.time.Instant
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toKotlinDuration
import kotlin.time.Duration as KotlinDuration

class GetElapsedTimeUseCase(
    private val puzzleSessionRepository: PuzzleSessionRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(puzzleId: String): Flow<KotlinDuration> {
        return puzzleSessionRepository.getAllSessionsBy(puzzleId).flatMapLatest { sessions ->
            val finishedDuration = sessions
                .filter { it.endedAt != null }
                .fold(Duration.ZERO) { acc, session ->
                    acc.plus(Duration.between(session.startedAt, session.endedAt))
                }

            val activeSession = sessions.find { it.endedAt == null }

            if (activeSession != null) {
                flow {
                    while (true) {
                        val activeDuration = Duration.between(activeSession.startedAt, Instant.now())
                        emit(finishedDuration.plus(activeDuration).toKotlinDuration())
                        delay(1.seconds)
                    }
                }
            } else {
                flow { emit(finishedDuration.toKotlinDuration()) }
            }
        }
    }
}
