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
        Log.d(tag, "Iniciando step de validação com LLM")
        
        try {
            if (!aiModelService.isReady()) {
                val initResult = aiModelService.initialize()
                Log.d(tag, "Inicialização da IA: ${if (initResult.isSuccess) "Sucesso" else "Falha (${initResult.exceptionOrNull()?.message})"}")

                if (initResult.isFailure) {
                    return text
                }
            }

            val prompt = promptRepository.getPrompt(
                task = AITask.WORD_VALIDATION,
                version = promptVersion,
                variables = mapOf("text" to text)
            )

            val result = aiModelService.generate(prompt)
            
            return result.getOrDefault(text).also {
                Log.d(tag, "Step de validação com LLM concluída")
            }
        } finally {
            aiModelService.close()
        }
    }
}
