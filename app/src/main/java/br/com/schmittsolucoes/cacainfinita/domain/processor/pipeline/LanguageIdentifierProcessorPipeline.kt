package br.com.schmittsolucoes.cacainfinita.domain.processor.pipeline

interface LanguageIdentifierProcessorPipeline {
    suspend fun process(text: String): List<String>
}
