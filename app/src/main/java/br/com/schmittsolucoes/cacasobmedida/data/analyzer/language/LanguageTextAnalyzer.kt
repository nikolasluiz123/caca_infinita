package br.com.schmittsolucoes.cacasobmedida.data.analyzer.language

interface LanguageTextAnalyzer {
    fun initialize()
    suspend fun analyze(text: String): String
}
