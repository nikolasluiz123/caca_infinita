package br.com.schmittsolucoes.cacainfinita.data.processor.pipeline

import android.util.Log
import br.com.schmittsolucoes.cacainfinita.data.processor.pipeline.steps.FilterByMaxLengthStep
import br.com.schmittsolucoes.cacainfinita.data.processor.pipeline.steps.LanguageFilterStep
import br.com.schmittsolucoes.cacainfinita.data.processor.pipeline.steps.NormalizeTextStep
import br.com.schmittsolucoes.cacainfinita.data.processor.pipeline.steps.RemoveDuplicatedWordsStep
import br.com.schmittsolucoes.cacainfinita.data.processor.pipeline.steps.RemoveOverlappingWordsStep
import br.com.schmittsolucoes.cacainfinita.data.processor.pipeline.steps.RemoveStopWordsStep
import br.com.schmittsolucoes.cacainfinita.data.provider.StopWordsProvider
import br.com.schmittsolucoes.cacainfinita.domain.model.GridDimensions
import br.com.schmittsolucoes.cacainfinita.domain.model.result.language.IdentifiedWord
import br.com.schmittsolucoes.cacainfinita.domain.model.result.language.LanguageAnalyzerResult
import br.com.schmittsolucoes.cacainfinita.domain.model.result.puzzle.PuzzleGenerationConfig
import br.com.schmittsolucoes.cacainfinita.domain.processor.pipeline.TextProcessorPipeline
import javax.inject.Inject
import kotlin.math.floor
import kotlin.math.sqrt

class ImageTextProcessorPipeline @Inject constructor(
    private val stopWordsProvider: StopWordsProvider,
): TextProcessorPipeline {
    override suspend fun process(
        result: LanguageAnalyzerResult,
        config: PuzzleGenerationConfig,
        gridDimensions: GridDimensions
    ): List<IdentifiedWord> {
        val tag = this@ImageTextProcessorPipeline::class.simpleName
        Log.d("DEBUG_PROCESS", "$tag: Iniciando pipeline de processamento de texto")

        val rowsSquared = gridDimensions.rows * gridDimensions.rows
        val columnsSquared = gridDimensions.columns * gridDimensions.columns
        val diagonal = floor(sqrt((rowsSquared + columnsSquared).toDouble())).toInt()

        val steps = listOf(
            LanguageFilterStep(config.languageSelection),
            NormalizeTextStep(),
            RemoveDuplicatedWordsStep(),
            RemoveStopWordsStep(stopWordsProvider, config.languageSelection),
            FilterByMaxLengthStep(diagonal),
            RemoveOverlappingWordsStep()
        )

        var processedWords = result.identifiedWords
        Log.d("DEBUG_PROCESS", "$tag: Palavras antes das steps: ${processedWords.size}")

        steps.forEach { step ->
            processedWords = step.process(processedWords)
            Log.d("DEBUG_PROCESS", "$tag: Após ${step::class.simpleName}: ${processedWords.size} palavras")
        }

        return processedWords.also {
            Log.d("DEBUG_PROCESS", "$tag: Pipeline de processamento de texto concluída. Palavras encontradas: ${it.size}")
        }
    }
}
