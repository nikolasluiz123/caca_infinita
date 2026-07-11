package br.com.schmittsolucoes.cacainfinita.domain.model.result.language

import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.Language

data class LanguageAnalyzerResult(
    val identifiedWords: List<IdentifiedWord>
) {
    fun getLanguages(): Set<Language> {
        return identifiedWords.map { it.language }.toSet()
    }
}
