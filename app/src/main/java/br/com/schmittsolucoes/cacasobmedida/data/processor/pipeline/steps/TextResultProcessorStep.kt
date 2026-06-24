package br.com.schmittsolucoes.cacasobmedida.data.processor.pipeline.steps

interface TextResultProcessorStep {
    fun process(text: String): String
}