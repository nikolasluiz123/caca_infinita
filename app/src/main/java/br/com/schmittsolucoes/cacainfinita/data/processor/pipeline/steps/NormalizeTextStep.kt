package br.com.schmittsolucoes.cacainfinita.data.processor.pipeline.steps

import android.util.Log
import br.com.schmittsolucoes.cacainfinita.domain.exception.NoValidWordsException
import br.com.schmittsolucoes.cacainfinita.domain.model.result.language.IdentifiedWord
import java.text.Normalizer

/**
 * Etapa responsável por normalizar as palavras.
 *
 * A normalização consiste em:
 * 1. Remover acentos (ex: "AÇÃO" torna-se "ACAO").
 * 2. Converter todos os caracteres para caixa alta (Upper Case).
 */
class NormalizeTextStep : IdentifiedWordProcessorStep {
    /**
     * Normaliza as palavras da lista.
     *
     * @param words A lista de palavras a serem normalizadas.
     * @return Uma lista de palavras normalizadas.
     */
    override suspend fun process(words: List<IdentifiedWord>): List<IdentifiedWord> {
        val tag = this@NormalizeTextStep::class.simpleName

        Log.d("DEBUG_PROCESS", "$tag: Iniciando step NormalizeText")

        val result = words.map { word ->
            val normalizedText = Normalizer.normalize(word.text, Normalizer.Form.NFD)
                .replace(Regex("\\p{InCombiningDiacriticalMarks}+"), "")
                .uppercase()
            word.copy(text = normalizedText)
        }

        if (result.isEmpty()) {
            throw NoValidWordsException()
        }

        return result.also {
            Log.d("DEBUG_PROCESS", "$tag: Fim step NormalizeText")
        }
    }
}
