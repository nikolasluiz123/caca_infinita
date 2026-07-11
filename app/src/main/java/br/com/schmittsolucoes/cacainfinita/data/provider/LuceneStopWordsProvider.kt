package br.com.schmittsolucoes.cacainfinita.data.provider

import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.LanguageSelection
import org.apache.lucene.analysis.en.EnglishAnalyzer
import org.apache.lucene.analysis.pt.PortugueseAnalyzer
import javax.inject.Inject

class LuceneStopWordsProvider @Inject constructor() : StopWordsProvider {
    
    override fun getStopWords(config: LanguageSelection): Set<String> {
        val stopWords = mutableSetOf<String>()
        
        if (config == LanguageSelection.PORTUGUESE_ONLY || config == LanguageSelection.BOTH) {
            val ptStopSet = PortugueseAnalyzer.getDefaultStopSet()
            stopWords.addAll(ptStopSet.map { it.toString().uppercase() })
        }
        
        if (config == LanguageSelection.ENGLISH_ONLY || config == LanguageSelection.BOTH) {
            val enStopSet = EnglishAnalyzer.getDefaultStopSet()
            stopWords.addAll(enStopSet.map { it.toString().uppercase() })
        }
        
        return stopWords
    }
}
