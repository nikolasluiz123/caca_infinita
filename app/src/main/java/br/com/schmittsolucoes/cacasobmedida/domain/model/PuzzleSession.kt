package br.com.schmittsolucoes.cacasobmedida.domain.model

import java.time.Instant

data class PuzzleSession(
    override val id: String,
    val puzzleId: String,
    val startedAt: Instant,
    val endedAt: Instant? = null
): UniqueDomain
