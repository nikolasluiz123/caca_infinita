package br.com.schmittsolucoes.cacainfinita.data.model.dto

import br.com.schmittsolucoes.cacainfinita.data.model.serializers.InstantSerializer
import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.Direction
import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.Language
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class WordDTO(
    val id: String,
    val puzzleId: String,
    val text: String,
    val startRow: Int,
    val startCol: Int,
    val direction: Direction,
    val language: Language,
    @Serializable(with = InstantSerializer::class)
    val foundDate: Instant? = null
)
