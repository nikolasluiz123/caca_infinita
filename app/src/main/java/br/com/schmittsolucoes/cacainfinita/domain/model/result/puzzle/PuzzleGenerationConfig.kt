package br.com.schmittsolucoes.cacainfinita.domain.model.result.puzzle

import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.LanguageSelection

data class PuzzleGenerationConfig(
    val languageSelection: LanguageSelection
)