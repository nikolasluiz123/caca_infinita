package br.com.schmittsolucoes.cacainfinita.data.processor.language

import br.com.schmittsolucoes.cacainfinita.data.analyzer.language.LanguageTextAnalyzer
import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.Language
import br.com.schmittsolucoes.cacainfinita.domain.model.result.language.IdentifiedWord
import br.com.schmittsolucoes.cacainfinita.domain.model.result.language.LanguageAnalyzerResult
import br.com.schmittsolucoes.cacainfinita.domain.processor.language.LanguageIdentifierProcessor
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import javax.inject.Inject

class MLKitLanguageIdentifierProcessor @Inject constructor(
    private val languageTextAnalyzer: LanguageTextAnalyzer
) : LanguageIdentifierProcessor {

    override suspend fun process(words: List<String>): LanguageAnalyzerResult = coroutineScope {
        languageTextAnalyzer.initialize()

        val uniqueWords = words.distinct().filter { it.isNotBlank() }
        val semaphore = Semaphore(MAX_PARALLEL_REQUESTS)

        val languageMap = uniqueWords.map { word ->
            async {
                semaphore.withPermit {
                    word to mapToLanguage(languageTextAnalyzer.analyze(word))
                }
            }
        }.awaitAll().toMap()

        val identifiedWords = words.map { word ->
            IdentifiedWord(
                text = word,
                language = languageMap[word] ?: Language.OTHER
            )
        }

        LanguageAnalyzerResult(identifiedWords)
    }

    private fun mapToLanguage(languageCode: String): Language {
        return when (languageCode) {
            PORTUGUESE_CODE -> Language.PORTUGUESE
            ENGLISH_CODE -> Language.ENGLISH
            else -> Language.OTHER
        }
    }

    companion object {
        private const val PORTUGUESE_CODE = "pt"
        private const val ENGLISH_CODE = "en"
        private const val MAX_PARALLEL_REQUESTS = 20
    }
}
