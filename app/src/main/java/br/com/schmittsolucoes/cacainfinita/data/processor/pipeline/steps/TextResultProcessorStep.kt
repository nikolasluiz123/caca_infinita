package br.com.schmittsolucoes.cacainfinita.data.processor.pipeline.steps

interface TextResultProcessorStep {
    suspend fun process(text: String): String
}