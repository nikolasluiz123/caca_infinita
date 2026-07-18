package br.com.schmittsolucoes.cacainfinita.data.processor.pipeline

import br.com.schmittsolucoes.cacainfinita.data.processor.pipeline.steps.CleanNoiseStep
import br.com.schmittsolucoes.cacainfinita.data.processor.pipeline.steps.FilterByMinLengthStep
import br.com.schmittsolucoes.cacainfinita.domain.processor.pipeline.LanguageIdentifierProcessorPipeline
import javax.inject.Inject

class ImageLanguageIdentifierProcessorPipeline @Inject constructor() : LanguageIdentifierProcessorPipeline {

    override suspend fun process(text: String): List<String> {
        val steps = listOf(
            CleanNoiseStep(),
            FilterByMinLengthStep(MIN_LENGTH)
        )

        var processedText = text
        steps.forEach { step ->
            processedText = step.process(processedText)
        }

        return processedText.split(Regex("\\s+")).filter { it.isNotBlank() }
    }

    companion object {
        private const val MIN_LENGTH = 3
    }
}
