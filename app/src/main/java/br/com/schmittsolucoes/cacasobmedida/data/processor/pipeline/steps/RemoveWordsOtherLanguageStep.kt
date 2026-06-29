package br.com.schmittsolucoes.cacasobmedida.data.processor.pipeline.steps

import android.util.Log
import br.com.schmittsolucoes.cacasobmedida.data.analyzer.language.LanguageTextAnalyzer

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
    override suspend fun process(text: String): String {
        val tag = this@RemoveWordsOtherLanguageStep::class.simpleName
        Log.d("DEBUG_PROCESS", "$tag: Iniciando step RemoveWordsOtherLanguage")

        languageTextAnalyzer.initialize()

        val words = text.split(Regex("\\s+")).filter { it.isNotBlank() }

        val filteredWords = words.filter { word ->
            val languageCode = languageTextAnalyzer.analyze(word)
            languageCode == PORTUGUESE_LANGUAGE_CODE
        }

        return filteredWords.joinToString(" ").also {
            Log.d("DEBUG_PROCESS", "$tag: Fim step RemoveWordsOtherLanguage. Palavras filtradas: ${words.size - filteredWords.size}")
        }
    }

    companion object {
        private const val PORTUGUESE_LANGUAGE_CODE = "pt"
    }
}
