package br.com.schmittsolucoes.cacasobmedida.data.processor.pipeline.steps

import android.util.Log

/**
 * Etapa responsável por remover palavras que são subconjuntos de outras palavras maiores.
 *
 * Em um caça-palavras, ter "CASA" e "CASAMENTO" pode ser redundante e confuso.
 * Esta etapa garante que apenas os termos mais longos e abrangentes sejam mantidos.
 */
class RemoveOverlappingWordsStep : TextResultProcessorStep {
    override suspend fun process(text: String): String {
        val tag = this@RemoveOverlappingWordsStep::class.simpleName
        Log.d("DEBUG_PROCESS", "$tag: Iniciando remoção de palavras sobrepostas")

        val words = text.split(Regex("\\s+"))
            .filter { it.isNotBlank() }
            .distinct()
            .sortedByDescending { it.length }

        val result = mutableListOf<String>()

        for (word in words) {
            if (result.none { it.contains(word) }) {
                result.add(word)
            }
        }

        return result.joinToString(" ").also {
            Log.d("DEBUG_PROCESS", "$tag: Fim da remoção. Palavras restantes: ${result.size}")
        }
    }
}
