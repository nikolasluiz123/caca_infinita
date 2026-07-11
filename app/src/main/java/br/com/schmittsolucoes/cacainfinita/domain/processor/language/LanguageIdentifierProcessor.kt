package br.com.schmittsolucoes.cacainfinita.domain.processor.language

import br.com.schmittsolucoes.cacainfinita.domain.model.result.language.LanguageAnalyzerResult

interface LanguageIdentifierProcessor {
    suspend fun process(words: List<String>): LanguageAnalyzerResult
}
