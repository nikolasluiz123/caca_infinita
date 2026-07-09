package br.com.schmittsolucoes.cacainfinita.data.processor.pipeline.steps

import android.util.Log
import br.com.schmittsolucoes.cacainfinita.domain.exception.NoValidWordsException
import java.text.Normalizer

/**
 * Etapa de normalização textual para padronização.
 *
 * Esta etapa prepara o texto para as fases de comparação (remoção de duplicatas e stop words)
 * e para a exibição final no jogo, garantindo consistência visual e lógica.
 */
class NormalizeTextStep : TextResultProcessorStep {
    /**
     * Aplica a normalização decompondo acentos, removendo-os e convertendo para caixa alta.
     *
     * O processo ocorre em três fases:
     * 1. **Normalizer.normalize(Form.NFD)**: Decompõe caracteres acentuados em seus componentes
     *    base e sinais diacríticos (ex: "ã" vira "a" + "~").
     * 2. **Regex \\p{InCombiningDiacriticalMarks}+**: Localiza e remove apenas os sinais
     *    diacríticos resultantes da decomposição, mantendo a letra base.
     * 3. **uppercase()**: Garante que todo o texto retorne em caixa alta para o padrão do jogo.
     *
     * @param text O texto a ser normalizado.
     * @return O texto sem acentos e em letras maiúsculas.
     */
    override suspend fun process(text: String): String {
        val tag = this@NormalizeTextStep::class.simpleName

        Log.d("DEBUG_PROCESS", "$tag: Iniciando step NormalizeText")

        val normalized = Normalizer.normalize(text, Normalizer.Form.NFD)
        val withoutAccents = normalized.replace(Regex("\\p{InCombiningDiacriticalMarks}+"), "")

        val result = withoutAccents.uppercase()

        if (result.isBlank()) {
            throw NoValidWordsException()
        }

        return result.also {
            Log.d("DEBUG_PROCESS", "$tag: Fim step NormalizeText")
        }
    }
}
