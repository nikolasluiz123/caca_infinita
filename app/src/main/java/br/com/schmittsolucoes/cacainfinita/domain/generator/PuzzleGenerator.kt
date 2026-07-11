package br.com.schmittsolucoes.cacainfinita.domain.generator

import br.com.schmittsolucoes.cacainfinita.domain.model.GridDimensions
import br.com.schmittsolucoes.cacainfinita.domain.model.result.language.IdentifiedWord
import br.com.schmittsolucoes.cacainfinita.domain.model.result.puzzle.PuzzleResult

interface PuzzleGenerator {
    suspend fun generate(words: List<IdentifiedWord>, dimensions: GridDimensions): List<PuzzleResult>
}
