package br.com.schmittsolucoes.cacainfinita.data.processor.pipeline.steps

import android.util.Log
import br.com.schmittsolucoes.cacainfinita.domain.exception.NoValidWordsException
import br.com.schmittsolucoes.cacainfinita.domain.model.result.language.IdentifiedWord

/**
 * Etapa responsável por remover palavras duplicadas.
 */
class RemoveDuplicatedWordsStep : IdentifiedWordProcessorStep {
    /**
     * Remove duplicatas da lista de palavras.
     *
     * @param words A lista de palavras a serem filtradas.
     * @return Uma lista contendo apenas palavras únicas.
     */
    override suspend fun process(words: List<IdentifiedWord>): List<IdentifiedWord> {
        val tag = this@RemoveDuplicatedWordsStep::class.simpleName

        Log.d("DEBUG_PROCESS", "$tag: Iniciando step RemoveDuplicatedWords")

        val result = words.distinctBy { it.text.uppercase() }

        if (result.isEmpty()) {
            throw NoValidWordsException()
        }

        return result.also {
            Log.d("DEBUG_PROCESS", "$tag: Fim step RemoveDuplicatedWords")
        }
    }
}
