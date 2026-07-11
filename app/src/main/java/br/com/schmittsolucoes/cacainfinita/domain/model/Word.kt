package br.com.schmittsolucoes.cacainfinita.domain.model

import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.Direction
import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.Language
import java.time.Instant

data class Word(
    override val id: String,
    val puzzleId: String,
    val text: String,
    val startRow: Int,
    val startCol: Int,
    val direction: Direction,
    val language: Language,
    val foundDate: Instant? = null,
): UniqueDomain
