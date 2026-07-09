package br.com.schmittsolucoes.cacainfinita.data.processor.pipeline.steps

import android.util.Log
import br.com.schmittsolucoes.cacainfinita.domain.exception.NoValidWordsException

/**
 * Etapa responsável por eliminar termos repetidos no texto.
 *
 * Esta etapa é essencial para que o caça-palavras contenha apenas palavras únicas,
 * evitando redundâncias na lista de busca do usuário.
 */
class RemoveDuplicatedWordsStep : TextResultProcessorStep {
    /**
     * Identifica e remove palavras duplicadas no fluxo de texto.
     *
     * O processamento utiliza:
     * 1. **Regex `\\s+`**: Divide o texto em tokens, lidando com múltiplos espaços em branco.
     * 2. **filter { it.isNotBlank() }**: Remove tokens vazios resultantes de espaços adjacentes.
     * 3. **distinct()**: Mantém apenas a primeira ocorrência de cada palavra encontrada.
     *
     * @param text O texto processado pelas etapas anteriores.
     * @return O texto contendo apenas palavras exclusivas separadas por um único espaço.
     */
    override suspend fun process(text: String): String {
        val tag = this@RemoveDuplicatedWordsStep::class.simpleName

        Log.d("DEBUG_PROCESS", "$tag: Iniciando step RemoveDuplicatedWords")

        val result = text.split(Regex("\\s+"))
            .filter { it.isNotBlank() }
            .distinct()
            .joinToString(" ")

        if (result.isBlank()) {
            throw NoValidWordsException()
        }

        return result.also {
            Log.d("DEBUG_PROCESS", "$tag: Fim step RemoveDuplicatedWords")
        }
    }
}
