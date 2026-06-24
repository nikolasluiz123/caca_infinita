package br.com.schmittsolucoes.cacasobmedida.data.processor.pipeline.steps

class RemoveStopWordsStep(private val stopWords: Set<String>): TextResultProcessorStep {
    override fun process(text: String): String {
        TODO("Not yet implemented")
    }
}