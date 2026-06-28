package br.com.schmittsolucoes.cacasobmedida.data.processor.pipeline.steps

import android.util.Log
import br.com.schmittsolucoes.cacasobmedida.domain.model.enumeration.AITask
import br.com.schmittsolucoes.cacasobmedida.domain.repository.PromptRepository
import br.com.schmittsolucoes.cacasobmedida.domain.service.AIModelService

/**
 * Etapa de validação de palavras utilizando um modelo de IA (LLM).
 *
 * Esta etapa envia o texto processado para o modelo para identificar e corrigir
 * palavras que possam ter sido extraídas de forma incompleta ou com erros que
 * as etapas anteriores não conseguiram resolver.
 */
class LLMWordValidationStep(
    private val aiModelService: AIModelService,
    private val promptRepository: PromptRepository,
    private val promptVersion: Int
) : TextResultProcessorStep {

    override suspend fun process(text: String): String {
        val tag = this@LLMWordValidationStep::class.simpleName
        Log.d("DEBUG_PROCESS", "$tag: Iniciando step de validação com LLM para ${text.length} caracteres")
        
        try {
            if (!aiModelService.isReady()) {
                val initResult = aiModelService.initialize()
                Log.d("DEBUG_PROCESS", "$tag: Inicialização da IA: ${if (initResult.isSuccess) "Sucesso" else "Falha (${initResult.exceptionOrNull()?.message})"}")

                if (initResult.isFailure) {
                    return text
                }
            }

            val promptTemplate = promptRepository.getPrompt(
                task = AITask.WORD_VALIDATION,
                version = promptVersion,
                variables = mapOf("language" to "Português")
            )

            val words = text.split(Regex("\\s+")).filter { it.isNotBlank() }
            val batches = words.chunked(100)
            val validatedWords = mutableListOf<String>()

            Log.d("DEBUG_PROCESS", "$tag: Processando ${words.size} palavras em ${batches.size} batches")

            batches.forEachIndexed { index, batch ->
                val batchText = batch.joinToString(" ")

                Log.d("DEBUG_PROCESS", "$tag: Enviando batch ${index + 1}/${batches.size} (${batch.size} palavras)")
                
                val result = aiModelService.generate(promptTemplate, batchText)
                
                result.onSuccess { validatedBatch ->
                    validatedWords.add(validatedBatch)
                }.onFailure {
                    Log.e(tag, "Falha no batch ${index + 1}: ${it.message}")
                    validatedWords.add(batchText)
                }
            }
            
            return validatedWords.joinToString(" ").also {
                Log.d("DEBUG_PROCESS", "$tag: Step de validação com LLM concluída. Total de caracteres final: ${it.length}")
            }
        } finally {
            aiModelService.close()
        }
    }
}
