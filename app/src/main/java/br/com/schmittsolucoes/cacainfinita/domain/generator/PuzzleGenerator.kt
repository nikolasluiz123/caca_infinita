package br.com.schmittsolucoes.cacainfinita.domain.generator

import br.com.schmittsolucoes.cacainfinita.domain.model.GridDimensions
import br.com.schmittsolucoes.cacainfinita.domain.model.result.puzzle.PuzzleResult

interface PuzzleGenerator {
    suspend fun generate(words: List<String>, dimensions: GridDimensions): List<PuzzleResult>
}