package br.com.schmittsolucoes.cacainfinita.data.processor.pipeline.steps

import android.util.Log

/**
 * Etapa responsável por remover palavras que possuem comprimento menor que o mínimo especificado.
 *
 * @param minLength O comprimento mínimo permitido para uma palavra.
 */
class FilterByMinLengthStep(private val minLength: Int) : TextResultProcessorStep {
    /**
     * Filtra o texto removendo palavras que possuem menos caracteres que o mínimo.
     *
     * @param text O texto a ser filtrado.
     * @return Uma String contendo apenas as palavras que possuem o tamanho mínimo.
     */
    override suspend fun process(text: String): String {
        val tag = this@FilterByMinLengthStep::class.simpleName

        Log.d("DEBUG_PROCESS", "$tag: Iniciando step FilterByMinLength (Min: $minLength)")

        return text.split(Regex("\\s+"))
            .filter { word -> word.isNotBlank() && word.length >= minLength }
            .joinToString(" ").also {
                Log.d("DEBUG_PROCESS", "$tag: Fim step FilterByMinLength")
            }
    }
}
