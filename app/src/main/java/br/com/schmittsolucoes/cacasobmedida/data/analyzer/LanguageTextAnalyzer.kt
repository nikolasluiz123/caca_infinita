package br.com.schmittsolucoes.cacasobmedida.data.analyzer

interface LanguageTextAnalyzer {
    fun initialize()
    suspend fun analyze(text: String): String
}