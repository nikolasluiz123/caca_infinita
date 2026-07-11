package br.com.schmittsolucoes.cacainfinita.data.processor.pipeline.steps

import android.util.Log
import br.com.schmittsolucoes.cacainfinita.data.provider.StopWordsProvider
import br.com.schmittsolucoes.cacainfinita.domain.exception.NoValidWordsException
import br.com.schmittsolucoes.cacainfinita.domain.model.result.language.IdentifiedWord
import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.LanguageSelection

/**
 * Etapa responsável por remover "Stop Words" (palavras irrelevantes) da lista de palavras identificadas.
 *
 * @param stopWordsProvider O provedor que fornece o conjunto de stop words.
 * @param config A configuração de seleção de idioma que define quais stop words devem ser carregadas.
 */
class RemoveStopWordsStep(
    private val stopWordsProvider: StopWordsProvider,
    private val config: LanguageSelection
): IdentifiedWordProcessorStep {
    /**
     * Processa a lista de palavras filtrando aquelas que constam como stop words.
     *
     * @param words A lista de [IdentifiedWord] a ser processada.
     * @return Uma lista contendo apenas as palavras que não são stop words.
     * @throws NoValidWordsException Caso todas as palavras sejam removidas pelo filtro.
     */
    override suspend fun process(words: List<IdentifiedWord>): List<IdentifiedWord> {
        val tag = this@RemoveStopWordsStep::class.simpleName
        Log.d("DEBUG_PROCESS", "$tag: Iniciando remoção de stop words")

        val stopWords = stopWordsProvider.getStopWords(config)

        val result = words.filter { word ->
            word.text.uppercase() !in stopWords
        }

        if (result.isEmpty()) {
            throw NoValidWordsException()
        }

        return result.also {
            Log.d("DEBUG_PROCESS", "$tag: Fim remoção de stop words. Palavras restantes: ${it.size}")
        }
    }
}
