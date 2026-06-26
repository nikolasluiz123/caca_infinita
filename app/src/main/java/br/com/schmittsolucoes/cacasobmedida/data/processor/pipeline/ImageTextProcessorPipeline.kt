package br.com.schmittsolucoes.cacasobmedida.data.processor.pipeline

import br.com.schmittsolucoes.cacasobmedida.domain.processor.pipeline.TextProcessorPipeline

import javax.inject.Inject

class ImageTextProcessorPipeline @Inject constructor(): TextProcessorPipeline {
    override fun process(text: String): List<String> {
        TODO("Not yet implemented")
    }
}