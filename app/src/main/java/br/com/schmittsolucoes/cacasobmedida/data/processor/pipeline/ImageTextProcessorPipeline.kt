package br.com.schmittsolucoes.cacasobmedida.data.processor.pipeline

import br.com.schmittsolucoes.cacasobmedida.domain.model.GridDimensions
import br.com.schmittsolucoes.cacasobmedida.domain.processor.pipeline.TextProcessorPipeline

import javax.inject.Inject

class ImageTextProcessorPipeline @Inject constructor(): TextProcessorPipeline {
    override suspend fun process(text: String, gridDimensions: GridDimensions): List<String> {
        TODO("Not yet implemented")
    }
}