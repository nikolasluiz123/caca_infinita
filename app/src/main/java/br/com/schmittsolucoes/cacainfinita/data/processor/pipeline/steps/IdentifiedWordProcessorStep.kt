package br.com.schmittsolucoes.cacainfinita.data.processor.pipeline.steps

import br.com.schmittsolucoes.cacainfinita.domain.model.result.language.IdentifiedWord

interface IdentifiedWordProcessorStep {
    suspend fun process(words: List<IdentifiedWord>): List<IdentifiedWord>
}