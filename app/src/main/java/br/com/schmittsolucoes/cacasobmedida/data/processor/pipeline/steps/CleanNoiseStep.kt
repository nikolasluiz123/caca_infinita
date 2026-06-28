package br.com.schmittsolucoes.cacasobmedida.data.processor.pipeline.steps

import android.util.Log

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

        Log.d(tag, "Iniciando step CleanNoise")

        return text.replace(Regex("[^\\p{L}\\s]"), " ").also {
            Log.d(tag, "Fim step CleanNoise")
        }
    }
}
