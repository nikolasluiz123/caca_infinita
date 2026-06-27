package br.com.schmittsolucoes.cacasobmedida.domain.generator

import br.com.schmittsolucoes.cacasobmedida.domain.model.GridDimensions
import br.com.schmittsolucoes.cacasobmedida.domain.model.result.puzzle.PuzzleResult

interface PuzzleGenerator {
    suspend fun generate(words: List<String>, dimensions: GridDimensions): List<PuzzleResult>
}