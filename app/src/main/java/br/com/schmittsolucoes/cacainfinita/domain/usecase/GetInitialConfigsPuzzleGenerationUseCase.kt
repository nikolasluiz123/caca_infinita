package br.com.schmittsolucoes.cacainfinita.domain.usecase

import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.Language
import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.LanguageSelection
import br.com.schmittsolucoes.cacainfinita.domain.model.result.puzzle.PuzzleGenerationConfig
import br.com.schmittsolucoes.cacainfinita.domain.provider.LanguageProvider
import br.com.schmittsolucoes.cacainfinita.domain.provider.OrientationProvider
import javax.inject.Inject

class GetInitialConfigsPuzzleGenerationUseCase @Inject constructor(
    private val languageProvider: LanguageProvider,
    private val orientationProvider: OrientationProvider
) {
    operator fun invoke(): PuzzleGenerationConfig {
        val systemLanguage = languageProvider.getSystemLanguage()
        val languageSelection = when (systemLanguage) {
            Language.PORTUGUESE -> LanguageSelection.PORTUGUESE_ONLY
            Language.ENGLISH -> LanguageSelection.ENGLISH_ONLY
            else -> LanguageSelection.ENGLISH_ONLY
        }

        val systemOrientation = orientationProvider.getSystemOrientation()

        return PuzzleGenerationConfig(
            languageSelection = languageSelection,
            orientation = systemOrientation
        )
    }
}
