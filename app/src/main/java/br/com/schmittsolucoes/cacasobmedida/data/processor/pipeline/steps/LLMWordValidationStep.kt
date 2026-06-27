package br.com.schmittsolucoes.cacasobmedida.data.processor.pipeline.steps

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
        try {
            if (!aiModelService.isReady()) {
                val initResult = aiModelService.initialize()

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
            
            return result.getOrDefault(text)
        } finally {
            aiModelService.close()
        }
    }
}
