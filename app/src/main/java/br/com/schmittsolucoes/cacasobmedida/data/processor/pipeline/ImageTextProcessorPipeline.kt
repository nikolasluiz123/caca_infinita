package br.com.schmittsolucoes.cacasobmedida.data.processor.pipeline

import android.util.Log
import br.com.schmittsolucoes.cacasobmedida.data.analyzer.language.LanguageTextAnalyzer
import br.com.schmittsolucoes.cacasobmedida.data.processor.pipeline.steps.CleanNoiseStep
import br.com.schmittsolucoes.cacasobmedida.data.processor.pipeline.steps.FilterByMaxLengthStep
import br.com.schmittsolucoes.cacasobmedida.data.processor.pipeline.steps.FilterByMinLengthStep
import br.com.schmittsolucoes.cacasobmedida.data.processor.pipeline.steps.NormalizeTextStep
import br.com.schmittsolucoes.cacasobmedida.data.processor.pipeline.steps.RemoveDuplicatedWordsStep
import br.com.schmittsolucoes.cacasobmedida.data.processor.pipeline.steps.RemoveOverlappingWordsStep
import br.com.schmittsolucoes.cacasobmedida.data.processor.pipeline.steps.RemoveStopWordsStep
import br.com.schmittsolucoes.cacasobmedida.data.processor.pipeline.steps.RemoveWordsOtherLanguageStep
import br.com.schmittsolucoes.cacasobmedida.data.provider.StopWordsProvider
import br.com.schmittsolucoes.cacasobmedida.domain.model.GridDimensions
import br.com.schmittsolucoes.cacasobmedida.domain.processor.pipeline.TextProcessorPipeline
import javax.inject.Inject
import kotlin.math.floor
import kotlin.math.sqrt

class ImageTextProcessorPipeline @Inject constructor(
    private val stopWordsProvider: StopWordsProvider,
    private val languageTextAnalyzer: LanguageTextAnalyzer,
): TextProcessorPipeline {
    override suspend fun process(text: String, gridDimensions: GridDimensions): List<String> {
        val tag = this@ImageTextProcessorPipeline::class.simpleName
        Log.d("DEBUG_PROCESS", "$tag: Iniciando pipeline de processamento de texto")

        val rowsSquared = gridDimensions.rows * gridDimensions.rows
        val columnsSquared = gridDimensions.columns * gridDimensions.columns
        val diagonal = floor(sqrt((rowsSquared + columnsSquared).toDouble())).toInt()

        val stopWords = stopWordsProvider.getStopWords()

        val steps = listOf(
            CleanNoiseStep(),
            FilterByMinLengthStep(5),
            RemoveWordsOtherLanguageStep(languageTextAnalyzer),
            NormalizeTextStep(),
            RemoveDuplicatedWordsStep(),
            RemoveStopWordsStep(stopWords),
            FilterByMaxLengthStep(diagonal),
            RemoveOverlappingWordsStep()
        )

        var processedText = text
        Log.d("DEBUG_PROCESS", "$tag: Palavras antes das steps: ${tokenize(processedText).size}")

        steps.forEach { step ->
            processedText = step.process(processedText)
            Log.d("DEBUG_PROCESS", "$tag: Após ${step::class.simpleName}: ${tokenize(processedText).size} palavras")
        }

        return tokenize(processedText).also {
            Log.d("DEBUG_PROCESS", "$tag: Pipeline de processamento de texto concluída. Palavras encontradas: ${it.size}")
        }
    }

    /**
     * Transforma o texto processado final em uma lista de palavras válidas.
     *
     * O processo realiza o split por qualquer quantidade de espaços em branco (Regex \\s+) e
     * remove espaços residuais nas extremidades de cada palavra.
     *
     * @param text O texto resultante da execução de todas as etapas da pipeline.
     * @return Uma lista de palavras prontas para a geração do caça-palavras.
     */
    private fun tokenize(text: String): List<String> {
        return text.split(Regex("\\s+"))
            .map { it.trim() }
            .filter { it.isNotBlank() }
    }
}