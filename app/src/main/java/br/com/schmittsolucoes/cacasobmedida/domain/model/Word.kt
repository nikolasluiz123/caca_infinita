package br.com.schmittsolucoes.cacasobmedida.domain.model

import br.com.schmittsolucoes.cacasobmedida.domain.model.enumeration.Direction
import java.time.Instant

data class Word(
    override val id: String,
    val puzzleId: String,
    val text: String,
    val startRow: Int,
    val startCol: Int,
    val direction: Direction,
    val foundDate: Instant? = null,
): UniqueDomain
