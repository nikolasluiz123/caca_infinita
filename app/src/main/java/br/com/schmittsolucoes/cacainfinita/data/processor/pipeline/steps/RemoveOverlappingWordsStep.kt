package br.com.schmittsolucoes.cacainfinita.data.processor.pipeline.steps

import android.util.Log
import br.com.schmittsolucoes.cacainfinita.domain.exception.NoValidWordsException

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

        if (result.isEmpty()) {
            throw NoValidWordsException()
        }

        return result.joinToString(" ").also {
            Log.d("DEBUG_PROCESS", "$tag: Fim da remoção. Palavras restantes: ${result.size}")
        }
    }
}
