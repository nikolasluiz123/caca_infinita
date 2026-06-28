package br.com.schmittsolucoes.cacasobmedida.data.provider

import org.apache.lucene.analysis.en.EnglishAnalyzer
import org.apache.lucene.analysis.pt.PortugueseAnalyzer
import javax.inject.Inject

class LuceneStopWordsProvider @Inject constructor() : StopWordsProvider {
    override fun getStopWords(): Set<String> {
        val stopSet = PortugueseAnalyzer.getDefaultStopSet() + EnglishAnalyzer.getDefaultStopSet()
        return stopSet.map { it.toString().uppercase() }.toSet()
    }
}
