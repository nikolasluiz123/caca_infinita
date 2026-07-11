package br.com.schmittsolucoes.cacainfinita.data.processor.pipeline.steps

import android.util.Log
import br.com.schmittsolucoes.cacainfinita.domain.exception.NoValidWordsException
import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.Language
import br.com.schmittsolucoes.cacainfinita.domain.model.result.language.IdentifiedWord
import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.LanguageSelection

class LanguageFilterStep(private val config: LanguageSelection): IdentifiedWordProcessorStep {

    override suspend fun process(words: List<IdentifiedWord>): List<IdentifiedWord> {
        val tag = this@LanguageFilterStep::class.simpleName
        Log.d("DEBUG_PROCESS", "$tag: Iniciando filtragem por idioma: $config")

        val filtered = words.filter { word ->
            when (config) {
                LanguageSelection.PORTUGUESE_ONLY -> word.language == Language.PORTUGUESE
                LanguageSelection.ENGLISH_ONLY -> word.language == Language.ENGLISH
                LanguageSelection.BOTH -> word.language == Language.PORTUGUESE || word.language == Language.ENGLISH
            }
        }

        if (filtered.isEmpty()) {
            throw NoValidWordsException()
        }

        return filtered.also {
            Log.d("DEBUG_PROCESS", "$tag: Fim filtragem. Palavras restantes: ${it.size}")
        }
    }
}
