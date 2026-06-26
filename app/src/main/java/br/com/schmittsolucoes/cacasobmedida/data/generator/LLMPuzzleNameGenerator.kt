package br.com.schmittsolucoes.cacasobmedida.data.generator

import br.com.schmittsolucoes.cacasobmedida.domain.generator.PuzzleNameGenerator
import br.com.schmittsolucoes.cacasobmedida.domain.model.result.puzzle.PuzzleResult

import javax.inject.Inject

class LLMPuzzleNameGenerator @Inject constructor(): PuzzleNameGenerator {
    override suspend fun generate(results: List<PuzzleResult>): List<PuzzleResult> {
        TODO("Not yet implemented")
    }
}