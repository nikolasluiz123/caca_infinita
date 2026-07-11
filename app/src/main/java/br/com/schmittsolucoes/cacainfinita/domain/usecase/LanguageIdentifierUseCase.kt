package br.com.schmittsolucoes.cacainfinita.domain.usecase

import br.com.schmittsolucoes.cacainfinita.domain.model.result.language.LanguageAnalyzerResult
import br.com.schmittsolucoes.cacainfinita.domain.processor.input.InputProcessor
import br.com.schmittsolucoes.cacainfinita.domain.processor.language.LanguageIdentifierProcessor
import br.com.schmittsolucoes.cacainfinita.domain.processor.pipeline.LanguageIdentifierProcessorPipeline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class LanguageIdentifierUseCase<INPUT>(
    private val inputProcessor: InputProcessor<INPUT>,
    private val pipeline: LanguageIdentifierProcessorPipeline,
    private val identifierProcessor: LanguageIdentifierProcessor
) {
    suspend operator fun invoke(input: INPUT): LanguageAnalyzerResult = withContext(Dispatchers.Default) {
        val rawText = inputProcessor.process(input)
        val filteredWords = pipeline.process(rawText)
        identifierProcessor.process(filteredWords)
    }
}
