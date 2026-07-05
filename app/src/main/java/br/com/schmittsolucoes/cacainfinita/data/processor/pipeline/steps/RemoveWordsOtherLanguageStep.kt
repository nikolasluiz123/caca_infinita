package br.com.schmittsolucoes.cacainfinita.data.processor.pipeline.steps

import android.util.Log
import br.com.schmittsolucoes.cacainfinita.data.analyzer.language.LanguageTextAnalyzer
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit

/**
 * Etapa responsável por filtrar palavras que não pertencem ao idioma português.
 *
 * Esta etapa utiliza o [LanguageTextAnalyzer] para identificar o idioma de cada palavra
 * individualmente. Palavras que não são identificadas como português (código "pt")
 * são removidas do fluxo de texto.
 */
class RemoveWordsOtherLanguageStep(
    private val languageTextAnalyzer: LanguageTextAnalyzer
) : TextResultProcessorStep {

    /**
     * Processa o texto filtrando palavras de outros idiomas.
     *
     * O processo consiste em:
     * 1. **Inicialização**: Garante que o analisador de idioma esteja pronto para uso.
     * 2. **Tokenização**: Divide o texto em palavras individuais usando espaços como delimitador.
     * 3. **Análise**: Para cada palavra, solicita a identificação do idioma ao [languageTextAnalyzer].
     * 4. **Filtragem**: Mantém apenas as palavras cujo idioma identificado seja "pt" (português).
     *
     * @param text O texto contendo as palavras a serem analisadas.
     * @return Uma string contendo apenas as palavras em português, separadas por espaço.
     */
    override suspend fun process(text: String): String = coroutineScope {
        val tag = this@RemoveWordsOtherLanguageStep::class.simpleName
        Log.d("DEBUG_PROCESS", "$tag: Iniciando step RemoveWordsOtherLanguage")

        languageTextAnalyzer.initialize()

        val allWords = text.split(Regex("\\s+")).filter { it.isNotBlank() }
        val uniqueWords = allWords.distinct()
        
        Log.d("DEBUG_PROCESS", "$tag: Palavras totais: ${allWords.size}, Palavras únicas: ${uniqueWords.size}")

        val semaphore = Semaphore(MAX_PARALLEL_REQUESTS)

        val languageMap = uniqueWords.map { word ->
            async {
                semaphore.withPermit {
                    word to languageTextAnalyzer.analyze(word)
                }
            }
        }.awaitAll().toMap()

        val filteredWords = allWords.filter { word ->
            languageMap[word] == PORTUGUESE_LANGUAGE_CODE
        }

        filteredWords.joinToString(" ").also {
            Log.d("DEBUG_PROCESS", "$tag: Fim step RemoveWordsOtherLanguage. Palavras filtradas: ${allWords.size - filteredWords.size}")
        }
    }

    companion object {
        private const val PORTUGUESE_LANGUAGE_CODE = "pt"
        private const val MAX_PARALLEL_REQUESTS = 20
    }
}
