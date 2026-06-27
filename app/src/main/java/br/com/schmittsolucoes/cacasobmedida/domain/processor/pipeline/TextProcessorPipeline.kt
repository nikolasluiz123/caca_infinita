package br.com.schmittsolucoes.cacasobmedida.domain.processor.pipeline

import br.com.schmittsolucoes.cacasobmedida.domain.model.GridDimensions

interface TextProcessorPipeline {
    suspend fun process(text: String, gridDimensions: GridDimensions): List<String>
}