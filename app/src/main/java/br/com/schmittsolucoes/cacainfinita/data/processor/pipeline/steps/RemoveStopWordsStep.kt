package br.com.schmittsolucoes.cacainfinita.data.processor.pipeline.steps

import android.util.Log
import br.com.schmittsolucoes.cacainfinita.domain.exception.NoValidWordsException

/**
 * Etapa responsável por filtrar palavras irrelevantes (Stop Words).
 *
 * Utiliza um conjunto de palavras fornecido por um [br.com.schmittsolucoes.cacainfinita.data.provider.StopWordsProvider]
 * para limpar o texto de conectivos e termos sem valor semântico para o jogo.
 *
 * @property stopWords O conjunto de palavras a serem removidas (devem estar em caixa alta).
 */
class RemoveStopWordsStep(private val stopWords: Set<String>) : TextResultProcessorStep {
    /**
     * Filtra o texto removendo tokens que pertencem ao conjunto de stop words.
     *
     * O processamento:
     * 1. Divide o texto em palavras usando o Regex `\\s+`.
     * 2. Filtra strings vazias e palavras que (em caixa alta) estejam presentes no conjunto [stopWords].
     * 3. Reconstrói a string com os termos restantes.
     *
     * @param text O texto a ser filtrado.
     * @return O texto limpo de palavras irrelevantes.
     */
    override suspend fun process(text: String): String {
        val tag = this@RemoveStopWordsStep::class.simpleName

        Log.d("DEBUG_PROCESS", "$tag: Iniciando step RemoveStopWords")

        val result = text.split(Regex("\\s+"))
            .filter { word -> word.isNotBlank() && word.uppercase() !in stopWords }
            .joinToString(" ")

        if (result.isBlank()) {
            throw NoValidWordsException()
        }

        return result.also {
            Log.d("DEBUG_PROCESS", "$tag: Fim step RemoveStopWords")
        }
    }
}
