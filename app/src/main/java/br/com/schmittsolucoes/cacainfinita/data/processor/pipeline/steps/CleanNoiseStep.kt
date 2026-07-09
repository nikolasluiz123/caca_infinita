package br.com.schmittsolucoes.cacainfinita.data.processor.pipeline.steps

import android.util.Log
import br.com.schmittsolucoes.cacainfinita.domain.exception.NoValidWordsException

/**
 * Etapa responsável por remover "ruídos" do texto extraído.
 *
 * Entende-se por ruído qualquer caractere que não seja uma letra funcional para o jogo,
 * tais como números, pontuações, símbolos matemáticos e caracteres especiais.
 */
class CleanNoiseStep : TextResultProcessorStep {
    /**
     * Processa o texto substituindo caracteres não alfabéticos por espaços.
     *
     * O Regex `[^\\p{L}\\s]` identifica tudo o que **não** é uma letra (em qualquer alfabeto,
     * incluindo acentuadas) e **não** é um espaço em branco. Ao substituir por espaços em vez
     * de remover, garantimos que palavras que estavam separadas apenas por um símbolo não sejam
     * "coladas" indevidamente.
     *
     * @param text O texto bruto extraído.
     * @return O texto limpo contendo apenas letras e espaços.
     */
    override suspend fun process(text: String): String {
        val tag = this@CleanNoiseStep::class.simpleName

        Log.d("DEBUG_PROCESS", "$tag: Iniciando step CleanNoise")

        val result = text.replace(Regex("[^\\p{L}\\s]"), " ")

        if (result.isBlank()) {
            throw NoValidWordsException()
        }

        return result.also {
            Log.d("DEBUG_PROCESS", "$tag: Fim step CleanNoise")
        }
    }
}
