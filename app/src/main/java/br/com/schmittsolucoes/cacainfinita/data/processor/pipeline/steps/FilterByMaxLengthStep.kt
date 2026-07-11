package br.com.schmittsolucoes.cacainfinita.data.processor.pipeline.steps

import android.util.Log
import br.com.schmittsolucoes.cacainfinita.domain.exception.NoValidWordsException
import br.com.schmittsolucoes.cacainfinita.domain.model.result.language.IdentifiedWord

/**
 * Etapa responsável por remover palavras que possuem comprimento maior que o máximo permitido.
 *
 * @param maxAllowedLength O comprimento máximo permitido para uma palavra.
 */
class FilterByMaxLengthStep(private val maxAllowedLength: Int) : IdentifiedWordProcessorStep {
    /**
     * Filtra a lista removendo palavras que possuem mais caracteres que o máximo.
     *
     * @param words A lista de palavras a ser filtrada.
     * @return Uma lista contendo apenas as palavras que possuem o tamanho máximo permitido.
     */
    override suspend fun process(words: List<IdentifiedWord>): List<IdentifiedWord> {
        val tag = this@FilterByMaxLengthStep::class.simpleName

        Log.d("DEBUG_PROCESS", "$tag: Iniciando step FilterByMaxLength (Max: $maxAllowedLength)")

        val result = words.filter { word -> word.text.length <= maxAllowedLength }

        if (result.isEmpty()) {
            throw NoValidWordsException()
        }

        return result.also {
            Log.d("DEBUG_PROCESS", "$tag: Fim step FilterByMaxLength")
        }
    }
}
