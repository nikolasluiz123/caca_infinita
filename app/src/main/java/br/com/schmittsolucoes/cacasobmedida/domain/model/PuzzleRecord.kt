package br.com.schmittsolucoes.cacasobmedida.domain.model

import kotlin.time.Duration

data class PuzzleRecord(
    override val id: String,
    val puzzleName: String,
    val wordsCount: Long,
    val duration: Duration
) : UniqueDomain
