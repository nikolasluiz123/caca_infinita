package br.com.schmittsolucoes.cacainfinita.data.analyzer.language

interface LanguageTextAnalyzer {
    fun initialize()
    suspend fun analyze(text: String): String
}
