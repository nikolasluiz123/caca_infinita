package br.com.schmittsolucoes.cacasobmedida.domain.generator

import br.com.schmittsolucoes.cacasobmedida.domain.model.result.puzzle.PuzzleResult

interface PuzzleNameGenerator {
    suspend fun generate(results: List<PuzzleResult>): List<PuzzleResult>
}