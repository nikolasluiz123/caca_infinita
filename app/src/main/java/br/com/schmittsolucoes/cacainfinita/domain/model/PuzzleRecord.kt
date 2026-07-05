package br.com.schmittsolucoes.cacainfinita.domain.model

import kotlin.time.Duration

data class PuzzleRecord(
    override val id: String,
    val puzzleName: String,
    val wordsCount: Long,
    val duration: Duration
) : UniqueDomain
