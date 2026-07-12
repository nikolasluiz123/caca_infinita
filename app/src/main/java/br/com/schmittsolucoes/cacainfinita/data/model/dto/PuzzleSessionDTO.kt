package br.com.schmittsolucoes.cacainfinita.data.model.dto

import br.com.schmittsolucoes.cacainfinita.data.model.serializers.InstantSerializer
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class PuzzleSessionDTO(
    val id: String,
    val puzzleId: String,
    @Serializable(with = InstantSerializer::class)
    val startedAt: Instant,
    @Serializable(with = InstantSerializer::class)
    val endedAt: Instant? = null
)
