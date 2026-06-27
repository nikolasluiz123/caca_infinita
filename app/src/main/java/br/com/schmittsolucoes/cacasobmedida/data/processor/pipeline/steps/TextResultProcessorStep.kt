package br.com.schmittsolucoes.cacasobmedida.data.processor.pipeline.steps

interface TextResultProcessorStep {
    suspend fun process(text: String): String
}