package br.com.schmittsolucoes.cacasobmedida.domain.processor.pipeline

interface TextProcessorPipeline {
    fun process(text: String): List<String>
}