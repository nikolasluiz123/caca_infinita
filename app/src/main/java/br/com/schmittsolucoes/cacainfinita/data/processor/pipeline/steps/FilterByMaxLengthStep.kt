package br.com.schmittsolucoes.cacainfinita.data.processor.pipeline.steps

import android.util.Log
import br.com.schmittsolucoes.cacainfinita.domain.exception.NoValidWordsException

/**
 * Etapa que filtra palavras com base no comprimento máximo permitido pela grade.
 *
 * Esta etapa garante que nenhuma palavra longa demais para caber na diagonal da matriz
 * avance no processo de geração.
 *
 * @property maxAllowedLength O comprimento máximo (geralmente a diagonal da grade).
 */
class FilterByMaxLengthStep(private val maxAllowedLength: Int) : TextResultProcessorStep {
    /**
     * Filtra o texto removendo palavras que excedem o limite de caracteres.
     *
     * O Regex `\\s+` é utilizado para dividir o texto em tokens baseando-se em qualquer
     * quantidade de espaços em branco. Em seguida, cada token é validado contra o
     * [maxAllowedLength].
     *
     * @param text O texto a ser filtrado.
     * @return Uma String contendo apenas as palavras que respeitam o limite de tamanho.
     */
    override suspend fun process(text: String): String {
        val tag = this@FilterByMaxLengthStep::class.simpleName

        Log.d("DEBUG_PROCESS", "$tag: Iniciando step FilterByMaxLength (Max: $maxAllowedLength)")

        val result = text.split(Regex("\\s+"))
            .filter { word -> word.isNotBlank() && word.length <= maxAllowedLength }
            .joinToString(" ")

        if (result.isBlank()) {
            throw NoValidWordsException()
        }

        return result.also {
            Log.d("DEBUG_PROCESS", "$tag: Fim step FilterByMaxLength")
        }
    }
}
