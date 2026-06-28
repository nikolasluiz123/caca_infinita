package br.com.schmittsolucoes.cacasobmedida.data.processor.pipeline

import android.util.Log
import br.com.schmittsolucoes.cacasobmedida.data.processor.pipeline.steps.CleanNoiseStep
import br.com.schmittsolucoes.cacasobmedida.data.processor.pipeline.steps.FilterByMaxLengthStep
import br.com.schmittsolucoes.cacasobmedida.data.processor.pipeline.steps.LLMWordValidationStep
import br.com.schmittsolucoes.cacasobmedida.data.processor.pipeline.steps.NormalizeTextStep
import br.com.schmittsolucoes.cacasobmedida.data.processor.pipeline.steps.RemoveDuplicatedWordsStep
import br.com.schmittsolucoes.cacasobmedida.data.processor.pipeline.steps.RemoveStopWordsStep
import br.com.schmittsolucoes.cacasobmedida.data.provider.StopWordsProvider
import br.com.schmittsolucoes.cacasobmedida.domain.model.GridDimensions
import br.com.schmittsolucoes.cacasobmedida.domain.processor.pipeline.TextProcessorPipeline
import br.com.schmittsolucoes.cacasobmedida.domain.repository.PromptRepository
import br.com.schmittsolucoes.cacasobmedida.domain.service.AIModelService
import javax.inject.Inject
import kotlin.math.floor
import kotlin.math.sqrt

class PDFTextProcessorPipeline @Inject constructor(
    private val stopWordsProvider: StopWordsProvider,
    private val aiModelService: AIModelService,
    private val promptRepository: PromptRepository
) : TextProcessorPipeline {

    override suspend fun process(text: String, gridDimensions: GridDimensions): List<String> {
        val tag = this@PDFTextProcessorPipeline::class.simpleName
        Log.d(tag, "Iniciando pipeline de processamento de texto")

        val rowsSquared = gridDimensions.rows * gridDimensions.rows
        val columnsSquared = gridDimensions.columns * gridDimensions.columns
        val diagonal = floor(sqrt((rowsSquared + columnsSquared).toDouble())).toInt()

        val stopWords = stopWordsProvider.getStopWords("pt")

        val steps = listOf(
            CleanNoiseStep(),
            NormalizeTextStep(),
            RemoveDuplicatedWordsStep(),
            RemoveStopWordsStep(stopWords),
            FilterByMaxLengthStep(diagonal),
            LLMWordValidationStep(aiModelService, promptRepository, 1)
        )

        var processedText = text

        steps.forEach { step ->
            processedText = step.process(processedText)
        }

        return tokenize(processedText).also {
            Log.d(tag, "Pipeline de processamento de texto concluída. Palavras encontradas: ${it.size}")
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
