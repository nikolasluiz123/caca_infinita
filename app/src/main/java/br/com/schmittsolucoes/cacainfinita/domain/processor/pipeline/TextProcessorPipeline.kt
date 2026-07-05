package br.com.schmittsolucoes.cacainfinita.domain.processor.pipeline

import br.com.schmittsolucoes.cacainfinita.domain.model.GridDimensions

interface TextProcessorPipeline {
    suspend fun process(text: String, gridDimensions: GridDimensions): List<String>
}