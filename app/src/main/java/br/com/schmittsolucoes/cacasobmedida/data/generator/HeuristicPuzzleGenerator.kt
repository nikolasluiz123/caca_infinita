package br.com.schmittsolucoes.cacasobmedida.data.generator

import br.com.schmittsolucoes.cacasobmedida.domain.model.GridDimensions
import br.com.schmittsolucoes.cacasobmedida.domain.generator.PuzzleGenerator
import br.com.schmittsolucoes.cacasobmedida.domain.model.result.puzzle.PuzzleResult

import javax.inject.Inject

class HeuristicPuzzleGenerator @Inject constructor(): PuzzleGenerator {
    override fun generate(words: List<String>, dimensions: GridDimensions): List<PuzzleResult> {
        TODO("Not yet implemented")
    }
}