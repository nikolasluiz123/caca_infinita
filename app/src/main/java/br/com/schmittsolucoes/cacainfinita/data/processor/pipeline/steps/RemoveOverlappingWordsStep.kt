package br.com.schmittsolucoes.cacainfinita.data.processor.pipeline.steps

import android.util.Log
import br.com.schmittsolucoes.cacainfinita.domain.model.result.language.IdentifiedWord

/**
 * Etapa responsável por remover palavras que estão contidas dentro de outras palavras mais longas.
 * Exemplo: Se tivermos "MACA" e "MACARRÃO", "MACA" será removida para evitar redundância na grade.
 */
class RemoveOverlappingWordsStep : IdentifiedWordProcessorStep {
    /**
     * Filtra a lista removendo termos sobrepostos.
     *
     * @param words A lista de palavras a ser filtrada.
     * @return Uma lista limpa de sobreposições.
     */
    override suspend fun process(words: List<IdentifiedWord>): List<IdentifiedWord> {
        val tag = this@RemoveOverlappingWordsStep::class.simpleName

        Log.d("DEBUG_PROCESS", "$tag: Iniciando step RemoveOverlappingWords")

        val sortedWords = words.sortedByDescending { it.text.length }
        val result = mutableListOf<IdentifiedWord>()

        for (word in sortedWords) {
            val isOverlap = result.any { existing -> existing.text.contains(word.text) }
            if (!isOverlap) {
                result.add(word)
            }
        }

        return result.also {
            Log.d("DEBUG_PROCESS", "$tag: Fim step RemoveOverlappingWords")
        }
    }
}
