package br.com.schmittsolucoes.cacasobmedida.data.provider

import org.apache.lucene.analysis.en.EnglishAnalyzer
import org.apache.lucene.analysis.pt.PortugueseAnalyzer
import javax.inject.Inject

class LuceneStopWordsProvider @Inject constructor() : StopWordsProvider {
    override fun getStopWords(language: String): Set<String> {
        val stopSet = when (language.lowercase()) {
            "pt" -> PortugueseAnalyzer.getDefaultStopSet()
            "en" -> EnglishAnalyzer.getDefaultStopSet()
            else -> PortugueseAnalyzer.getDefaultStopSet()
        }
        
        return stopSet.map { it.toString().uppercase() }.toSet()
    }
}
